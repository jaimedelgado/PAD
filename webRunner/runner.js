
//Para testear el juego en local sin la parte nativa.
/*var DatosNativos = (function(){
  return{
  monedas: function(){
    return 110;
  },
  nivelEscudo: function(){
    return 0;
  },
  nivelFuego: function(){
    return 0;
  },
  sumar: function(cantidad){
    console.log("monedaSumada");
  },
  maxPuntuacion: function(puntos){
    console.log("puntos: " + puntos);
  }
  }
})();*/

//Función autoejecutable para tomar los datos de la parte nativa gracias al JavascriptInterface.
var DatosLocal = (function(){
  return{
    numMonedas : DatosNativos.monedas() > 0 ? DatosNativos.monedas() : 0,
    nivelEscudo: DatosNativos.nivelEscudo() > 0 ? DatosNativos.nivelEscudo() : 0,
    nivelFuego: DatosNativos.nivelFuego() > 0 ? DatosNativos.nivelFuego() : 0,
    distanciaMaxima: 0
  }
})();


window.addEventListener("load",function() {


//Quintus es el motor gráfico que usamos para desarrollar el juego.
var Q = window.Q = Quintus()
        .include("Sprites, Scenes, Input, 2D, Anim, Touch, UI")//Módulos que utilizaremos durante el desarrollo.
        .setup({ width: 683, height: 384, scaleToFit: true })//Tamaño "por defecto". scaleToFit nos permite escalar
        .controls().touch();                                  //y ajustar la pantalla a cualquier configuración.



/////////////////////////////////DISTANCIA///////////////////////////////////////////

//Texto con la información de la distancia recorrida que aparecerá en la parte superior izquierda.
  Q.UI.Text.extend("Score",{
    init:function(p){
      this._super({label:"Distancia: 0m",x:600,y:0});
      Q.state.on("change.score",this,"score");
    },
    score: function(score){
      this.p.label="Distancia: " + score + "m";
    }
  });

  Q.scene("HUD",function(stage){
    var container = stage.insert(new Q.UI.Container({
      x:0,y:0
    }));
    var label= container.insert(new Q.Score());
    Q.state.set("score",0);
  });
////////////////////////////COINS INFO/////////////////////////////////////////////////////

//Texto con la información de las monedas recogidas que aparecerá en la parte superior izquierda (debajo de la distancia)

  Q.UI.Text.extend("Coins",{
    init:function(p){
      this._super({ label:"Coins: " + DatosLocal.numMonedas,x:615,y:30});
      Q.state.on("change.coins",this,"coins");
    },
    coins: function(coins){
      this.p.label="Coins: " + coins;
    }
  });

  Q.scene("HUDCoins",function(stage){
    var container = stage.insert(new Q.UI.Container({
      x:0,y:0
    }));
    var label= container.insert(new Q.Coins());
    Q.state.set("coins",DatosLocal.numMonedas);
  });

  //Tipos posibles que pueden tener los objetos para definir los que tienen que colisionar entre si.
var SPRITE_BOX = 1;
var SPRITE_MINA = 2;

//Gravedad que se usará en todo el juego.
Q.gravityY = 2000;

//Clase principal con la información del "player".
Q.Sprite.extend("Player",{

  init: function(p) {

    this._super(p,{
      sheet: "eater",//Hoja de sprites que usará.
      sprite: "eater",
      collisionMask: SPRITE_BOX,//Tipo de objeto con el que detectar colisiones.
      doubleJump: true,
      releaseJump: false,//Tanto doubleJump como releaseJump nos sirve para implementar el doble salto del personaje.
      x: 40,
      shield: false,//variable para saber si el jugador tiene el escudo activado.
      onFire: false,//variable para saber si el jugador tiene el fuego activado.
      y: 610,
      scale: 0.8,//escalamos la imagen para que se vea más pequeño.
      standingPoints: [ [ -16, 44], [ -23, 35 ], [-23,-48], [23,-48], [23, 35 ], [ 16, 44 ]],
      duckingPoints : [ [ -16, 44], [ -23, 35 ], [-23,-10], [23,-10], [23, 35 ], [ 16, 44 ]],
      speed: 500,
      jump: -550,//valor del salto.
      stopped: false,//Variable para saber si el jugador está parado (chocando con una caja)
      dead: false,//Variable para saber si el jugador está muerto.
      distance: 0,//distancia recorrida
      ameba: 0,//referencia a la ameba para poder destruirla cuando colisione con una mina.
      nivelAgachado: 0
    });

    console.log("coins " + DatosLocal.numMonedas + " escudo: " + DatosLocal.nivelEscudo + " fuego: " + DatosLocal.nivelFuego);
    this.p.points = this.p.standingPoints;
    this.add("2d, animation");//módulos que usará esta clase, el 2d para colisiones y el de animation para hacer que se mueva.
    this.play("walk");//comenzará con la animación de andar.
  },

  dead: function(){
    this.p.dead = true;
  },

  setAmeba: function(amebaObj){
    this.p.ameba = amebaObj;
  },


  setFire: function(activated){
     if(activated)
      this.p.onFire = true;
    else
      this.p.onFire = false;
  },

  stopped: function(){
    return this.p.stopped;
  },

  setShield : function(activated){
    if(activated)
      this.p.shield = true;
    else
      this.p.shield = false;
  },

  step: function(dt) {

   if(!this.p.dead){
    this.p.vx += (this.p.speed - this.p.vx)/4;
    if(this.p.speed - this.p.vx < 3 ){//si el jugador no está parado contamos metros.
      this.p.distance += 1;
      if(this.p.distance == 100){
        this.p.distance = 0;
        Q.state.inc("score", 1);// cada 100 unidades de espacio recorridas se añade 1 "metro"
        this.p.stopped = false;
      }
    }else{
      this.p.stopped = true;
    }


    if(this.p.y > 610 || this.p.vy == 0 ) {
      if(this.p.y > 610)
        this.p.y = 610; 
      this.p.landed = 1;
      this.p.vy = 0;
    } else {
      this.p.landed = 0;
    }


    //////////////////////////////////////////////////////////////////////////////////
    //                  GESTIÓN DE LOS TOUCH INPUTS                                 //
    //////////////////////////////////////////////////////////////////////////////////
    if(this.p.doubleJump && this.p.releaseJump){
      if(Q.inputs['up']){
        //this.stage.insert(new Q.Ameba());

        this.p.vy = this.p.jump;
        this.p.doubleJump = false;
      }
    }

    if(Q.inputs['up'] && this.p.landed > 0 ) {
        this.p.vy = this.p.jump;
    }

    this.p.points = this.p.standingPoints;
    if(this.p.landed) {
      this.p.doubleJump = true;
      this.p.releaseJump = false;
      if(Q.inputs['down'] && this.p.y >=610 && this.p.animation != "eat") {

        this.p.sheet = "agachado";
        this.p.sprite = "agachado";
        this.size(true);
        this.play("agachado");
        //this.size(true);
        this.p.points = this.p.duckingPoints;
      } else {
        if(this.p.sheet == "agachado"){
          this.p.sheet = "eater";
          this.p.sprite = "eater";
          this.size(true);
        }
       // this.p.sprite = "eater";
        if(this.p.animation != "eat")
          this.play("walk");
      }
    } else {
      if(!Q.inputs['up']){
        this.p.releaseJump = true;
      }
      if(this.p.animation != "eat" && this.p.vy < 0)
        this.play("jump_up");
      else if(this.p.animation != "eat"){
        this.play("jump_down");
      }
    }

    this.stage.viewport.centerOn(this.p.x + 175, 500 );//La cámara se centrará en el personaje.
  }
  }
});

//Objeto enemigo principal.
Q.Sprite.extend("Mina",{
  init: function() {
    var levels = [  540,  450, 610 ];//Alturas a las que se lanzarán las minas.
    var player = Q("Player").first();//Referencia al player principal para trabajar con las colisiones.
    this._super({
      player : Q("Player").first(),
      x: player.p.x + Q.width + 50,
      y: levels[Math.floor(Math.random() * 3)],
      frame: Math.random() < 0.5 ? 0 : 0,
      scale: 1.2,
      type: SPRITE_BOX,
      sheet: "mina",
      vx: - 200,
      vy: 0,
      ay: 0,
      theta: (300 * Math.random() + 200) * (Math.random() < 0.5 ? 1 : -1)//ángulo para hacer las rotaciones.
    });
    this.add("animation, tween"); //Módulos a usar. El tween permite animaciones trabajando directamente con 
                                  //el sprite(agrandarlo, hacerlo transparente...)
                                  
    this.on("hit");//Cuando hay una colisión, el callback es la función hit();
  },
  step: function(dt) {//Actualización de la posición de la mina.
    this.p.x += this.p.vx * dt;


    this.p.vy += this.p.ay * dt;
    this.p.y += this.p.vy * dt;
    if(this.p.y != 565) {
      this.p.angle += this.p.theta * dt;
    }
    if(this.p.y > 800 || this.p.x < this.p.player.p.x - 200) {this.destroy(); }

  },
  hit: function(collision) {
    //Lógica de las colisiones dependiendo si el player está con el escudo o con el fuego.
    var player = collision.obj;
    if(this.p.type != 0 && player.isA("Player") && (player.p.onFire)){
      this.p.type = 0;
      player.play("eat");
      var playerVel = player.p.vx;
      //console.log("caja: x-> " + this.p.x + " y-> " + this.p.y + " |  Pj: x-> " + collision.obj.p.x + " y-> " + collision.obj.p.y);
      this.animate({y: player.p.y, vx: playerVel + playerVel/1.5, opacity: 0, scale: 0.1}, 1/5, {callback: function(){this.destroy();}});
    }else if(this.p.type != 0 && player.isA("Player") && (player.p.shield)){
      this.p.type = 0;
      this.p.opacity = 0.5;
      player.p.ameba.hitted();

    }else if(this.p.type != 0 && player.isA("Player")){
      player.p.vx = 0;
      player.dead();
      Q.stageScene("endGame", 1, { label: "Game Over!" });
      this.p.type = 0;
      this.p.collisionMask = Q.SPRITE_NONE;

      this.p.opacity = 0.5;
    }
      this.p.vx = 200;
      this.p.ay = 400;
      this.p.vy = -300;
  }
});


//TODO: Clase que representa las cajas que se puede comer el jugador 
Q.Sprite.extend("BoxGood",{
  init: function() {

    var levels = [ 570 , 500, 450 ];

    var player = Q("Player").first();
    this._super({
      touched: false,
      x: player.p.x + Q.width + 50,
      y: levels[Math.floor(Math.random() * 3)],
      //frame: Math.random() < 0.5 ? 1 : 0,
      scale: 0.9,
      type: SPRITE_BOX,
      sheet: "mina",
      vx: -200 + 400 * Math.random(),
      vy: 0,
      ay: 0,
      sensor: true,
      theta: (300 * Math.random() + 200) * (Math.random() < 0.5 ? 1 : -1)
    });

    this.add("animation, tween");

    this.on("hit", this, "sensor");

    //this.add("animation, tween");
  },

  step: function(dt) {
    this.p.x += this.p.vx * dt;


    this.p.vy += this.p.ay * dt;
    this.p.y += this.p.vy * dt;
    if(this.p.y != 565) {
      this.p.angle += this.p.theta * dt;
    }

    if(this.p.y > 800) { this.destroy(); }

  },

  sensor: function(collision) {
    var playerObj = collision.obj;
    if(!this.p.touched && playerObj.isA("Player")){
      this.p.touched = true; 
      playerObj.play("eat");
      var playerVel = playerObj.p.vx;
      //console.log("caja: x-> " + this.p.x + " y-> " + this.p.y + " |  Pj: x-> " + collision.obj.p.x + " y-> " + collision.obj.p.y);
      this.animate({y: playerObj.p.y, vx: playerVel + playerVel/1.5, opacity: 0, scale: 0.1}, 1/5, {callback: function(){this.destroy();}});
    }
  }
  

});


//Clase que representa las cajas fijas que habrá por el mapa.
Q.Sprite.extend("BoxFija",{
  init: function(posX, posY) {//posX es la posición en la que queremos que aparezca en el eje X
                              //posY es la posición en la que queremos que aparezca en el eje Y

    var levels = [ 573 , 515 , 509 ];

    var player = Q("Player").first();
    this._super({
      player: Q("Player").first(),
      touched: false,
      x: player.p.x + Q.width + 50,
      y: 610 - (50 * posY),
      scale: 0.8,
      type: SPRITE_BOX,
      sheet: "cajaBuena",
      vx: 50,
      vy: 0,
      ay: 0,
    });
    this.p.x += this.p.w * this.p.scale * posX;
    this.add("animation, tween");
    this.on("hit");
  },

  step: function(dt) {
    this.p.x += this.p.vx * dt;


    this.p.vy += this.p.ay * dt;
    this.p.y += this.p.vy * dt;
    
    if(this.p.y > 800 || this.p.x < this.p.player.p.x - 200) { this.destroy(); }

  },

  hit: function(collision) {
    var playerObj = collision.obj;
    if(!this.p.touched && playerObj.isA("Player") && playerObj.p.onFire){
      this.p.touched = true; 
      this.p.type = 0;
      var playerVel = playerObj.p.vx;
      this.animate({y: playerObj.p.y, vx: playerVel + playerVel/2, opacity: 0, scale: 0.1}, 1/5, {callback: function(){this.destroy();}});
    }
  }
});

//Clase que representa el escudo cuando está activado (aura azul en el personaje)
Q.Sprite.extend("Ameba",{
  init: function(level) {
    var player = Q("Player").first();
    this._super({
      player: Q("Player").first(),
      x: player.p.x,
      y: player.p.y,
      scale: 1.1,
      type: 0,
      opacity: 0.4,
      sheet: "ameba",
      sprite: "ameba",
      timeStart: 0,
      timeMax: level > 0 ? 5*level : 5,//Duración del escudo, se toma el nivel del escudo desde la parte nativa.
      vx: player.p.vx,
      vy: player.p.vy,
      ay: player.p.ay,
    });

    this.add("animation, tween");
    
    this.play("ameba");

    this.on("hit", this, "sensor");

  },
    
  hitted: function(){
      this.p.player.setShield(false);
      this.animate({scale: 0.3, opacity: 0.1}, 1/3, {callback: function(){this.destroy();}});
  },


  step: function(dt) {

    this.p.timeStart += dt;
    this.p.x = this.p.player.p.x;

    this.p.vy = this.p.player.p.vy;
    this.p.y = this.p.player.p.y;
    
    if(this.p.timeStart > this.p.timeMax){ 
      this.p.player.setShield(false); 
      this.destroy();
    }

  },

  sensor: function(collision) {
    if(collision.obj.isA("Mina")){
      collision.obj.p.type = 0;
      collision.obj.p.opacity = 0.5;
    }
  }
  

});
//Clase que representa el icono del escudo que aparece por el mapa y el jugador puede coger.
Q.Sprite.extend("Escudo",{
  init: function(tipo) {

    var player = Q("Player").first();
    this._super({
      player: Q("Player").first(),
      touched: false,
      x: player.p.x + Q.width + 50,
      y: 509,
      scale: 1,
      type: SPRITE_BOX,
      opacity: 0.8,
      sheet: "escudo",
      sprite: "escudo",
      sensor: true,
      vx: 50,
      vy: 0,
      ay: 0,
    });

    this.add("animation, tween");
    this.animateShield();
    this.on("hit", this, "sensor");

    },

  animateShield: function(){
    this
      .animate({scale: 0.7}, 0.7 )
      .chain({scale: 1}, 0.7, {
        callback: function(){this.animateShield();}});
  },
  step: function(dt) {

    this.p.x += this.p.vx * dt;


    this.p.vy += this.p.ay * dt;
    this.p.y += this.p.vy * dt;


    if(this.p.y > 800 || this.p.x < this.p.player.p.x - 200) { this.destroy(); }


  },

  sensor: function(collision) {
    var playerObj = collision.obj;
    if(!this.p.touched && playerObj.isA("Player")){
      playerObj.play("eat");
      this.p.touched = true;
      if(!playerObj.p.shield){
        var ameba = new Q.Ameba(DatosLocal.nivelEscudo);//Cuando el jugador coje el escudo aparece una ameba que sirve como
                                                        //protección. (Aguanta un golpe).
        this.stage.insert(ameba);
        playerObj.setShield(true);
        playerObj.setAmeba(ameba);
      }
      this.animate({y: this.p.y - 30}, 1/5, {callback: function(){this.destroy();}});
    }else{
    }
  }
});

/////////////////////////////////////////////////////////////////////////////

//Clase que representa el icono del fuego.
Q.Sprite.extend("FuegoIcon",{
  init: function(tipo) {

    var levels = [ 573 , 515 , 509 ];

    var player = Q("Player").first();
    this._super({

      player: Q("Player").first(),
      touched: false,
      x: player.p.x + Q.width + 50,
      y: 509,
      scale: 0.2,
      type: SPRITE_BOX,

      opacity: 0.8,
      sheet: "fuego",
      sprite: "fuego",
      sensor: true,
      vx: 50,
      vy: 0,
      ay: 0,
    });

    this.add("animation, tween");
    this.animateFire();
    this.on("hit", this, "sensor");

    },

  animateFire: function(){
    this
      .animate({scale: 0.4}, 0.7 )
      .chain({scale: 0.7}, 0.7, {
        callback: function(){this.animateFire();}});
  },
  step: function(dt) {

    this.p.x += this.p.vx * dt;


    this.p.vy += this.p.ay * dt;
    this.p.y += this.p.vy * dt;


    if(this.p.y > 800 || this.p.x < this.p.player.p.x - 200) { this.destroy(); }


  },

  sensor: function(collision) {
    var playerObj = collision.obj;
    if(!this.p.touched && playerObj.isA("Player")){
      playerObj.play("eat");
      this.p.touched = true;
      if(!playerObj.p.onFire){
        this.stage.insert(new Q.Fuego(DatosLocal.nivelFuego));
        playerObj.setFire(true);
      }
      this.animate({y: this.p.y - 30}, 1/5, {callback: function(){this.destroy();}});
    }
  }
  

});
///////////////////////////////////////////////////////////////////////////

//Segundo escudo del personaje, es más potente porque no termina cuando colisiona con una mina y destruye todo lo que toca.
Q.Sprite.extend("Fuego",{
  init: function(level) {

    var player = Q("Player").first();
    this._super({
      player: Q("Player").first(),
      x: player.p.x,
      y: player.p.y - 20,
      scale: 1.1,
      type: 0,
      opacity: 0.6,
      sheet: "fuego",
      sprite: "fuego",
      timeStart: 0,
      timeMax: level > 0 ? 5*level : 5,
      vx: player.p.vx,
      vy: player.p.vy,
      ay: player.p.ay,
    });

    this.add("animation, tween");
    
    this.play("fuego");

    this.on("hit", this, "sensor");

    },

  step: function(dt) {

    this.p.timeStart += dt;
    this.p.x = this.p.player.p.x;

    this.p.vy = this.p.player.p.vy;
    this.p.y = this.p.player.p.y - 20;
    
    if(this.p.timeStart > this.p.timeMax){ 
      this.p.player.setFire(false); 
      this.destroy();
    }

  },

  sensor: function(collision) {
    
  }
  

});


//////////////////////////////COIN////////////////////////////////

//Clase que representa las monedas. La posición de la moneda se indica igual que en las cajas.
Q.Sprite.extend("Coin",{
  init: function(posX, posY) {

    var levels = [ 573 , 515 , 509 ];

    var player = Q("Player").first();
    this._super({
      player: Q("Player").first(),
      touched: false,      
      x: player.p.x + Q.width + 50,
      y: 610 - (50 * posY),
      scale: 0.7,
      type: SPRITE_BOX,
      opacity: 1,
      sheet: "coin",
      sprite: "coin",
      sensor: true,
      vx: 50,
      vy: 0,
      ay: 0,
      theta: (300 * Math.random() + 200) * (Math.random() < 0.5 ? 1 : -1)
    });
    this.p.x += this.p.w * this.p.scale * posX + 20;

    this.add("tween");
    
    this.animateCoin();
    this.on("hit", this, "sensor");

    },

  step: function(dt) {

    this.p.x += this.p.vx * dt;

    if(this.p.y != 565) {
      this.p.angle += this.p.theta * dt;
    }

    this.p.vy += this.p.ay * dt;
    this.p.y += this.p.vy * dt;
    
    if(this.p.y > 800 || this.p.x < this.p.player.p.x - 200) { this.destroy(); }

  },

  animateCoin: function(){
    this
      .animate({scale: 0.4}, 1 )
      .chain({scale: 0.7}, 1, {
        callback: function(){this.animateCoin();}});
  },

  sensor: function(collision) {
    var playerObj = collision.obj;
    if(!this.p.touched && playerObj.isA("Player")){
      this.p.touched = true;

      DatosNativos.sumar(1);
      Q.state.inc("coins", 1);
      this.animate({y: this.p.y - 30}, 1/5, {callback: function(){this.destroy();}});
    }
  }
  

});

///////////////////////////////////////////////////////////////



Q.GameObject.extend("BoxThrower",{//Spawner de "escenarios".
                                  //Para no estar dibujando un mapa infinito que puede ser una tarea larga y aburrida definimos
                                  //una serie de estructuras tipo (dos cajas apiladas, tres cajas pegadas con una moneda al final...)
                                  //que se irán lanzando cada cierto tiempo. Así conseguimos un mapa infinito y siempre será distinto.
                                  //TODO: Implementar más estructuras.
  init: function() {
    this.p = {
      player: null,
      launchDelay: 0.75,
      launchRandom: 1,
      launch: 1
    }
  },

  struct1: function(){

          this.stage.insert(new Q.BoxFija(1,0));
          this.stage.insert(new Q.Coin(2,0));
          this.stage.insert(new Q.BoxFija(2,1));
          this.stage.insert(new Q.BoxFija(3,0));
  },
  struct2: function(){
          this.stage.insert(new Q.BoxFija(1,0));
          this.stage.insert(new Q.BoxFija(2,0));
          this.stage.insert(new Q.BoxFija(4,2));
          this.stage.insert(new Q.BoxFija(5,2));
          this.stage.insert(new Q.BoxFija(6,2));
          this.stage.insert(new Q.BoxFija(8,0));
          this.stage.insert(new Q.BoxFija(9,0));
          this.stage.insert(new Q.Coin(3,0));
          this.stage.insert(new Q.Coin(4,0));
          this.stage.insert(new Q.Coin(5,0));
          this.stage.insert(new Q.Coin(6,0));
          this.stage.insert(new Q.Coin(7,0));
          
  },

  struct3: function(){
          this.stage.insert(new Q.BoxFija(1,0));
          this.stage.insert(new Q.BoxFija(2,0));
          this.stage.insert(new Q.BoxFija(4,2));
          this.stage.insert(new Q.BoxFija(5,2));
          this.stage.insert(new Q.BoxFija(6,2));
          this.stage.insert(new Q.BoxFija(8,4));
          this.stage.insert(new Q.BoxFija(9,4));
          this.stage.insert(new Q.Coin(3,0));
          this.stage.insert(new Q.Coin(4,0));
          this.stage.insert(new Q.Coin(5,0));
          this.stage.insert(new Q.Coin(6,0));
          this.stage.insert(new Q.Coin(7,0));
          this.stage.insert(new Q.Coin(10,5));
          this.stage.insert(new Q.Coin(9,5));
          this.stage.insert(new Q.Coin(11,4));
          this.stage.insert(new Q.Coin(12,4));

          
  },

  update: function(dt) {
    if(this.p.player==null)
      this.p.player=Q("Player").first();

    if(this.p.player && !this.p.player.stopped()){
      this.p.launch -= dt;


      var powerUp = Math.floor(Math.random()*6);

      if(this.p.launch < 0 ) {

      if(this.p.player.p.distance > 1000)
          this.stage.insert(new Q.Mina());

          var struct = Math.floor(Math.random()*3);
        if(powerUp===0){
          this.stage.insert(new Q.Mina());
          Math.floor(Math.random()*2)==0 ? this.stage.insert(new Q.FuegoIcon()) : this.stage.insert(new Q.Escudo());
          this.p.launch = this.p.launchDelay + this.p.launchRandom * Math.random();
        
        }else
        if(struct===0){
          this.struct3();
          this.stage.insert(new Q.Mina());
          this.p.launch = this.p.launchDelay + this.p.launchRandom * Math.random();
        }else if(struct===1){
          this.struct1();
          this.stage.insert(new Q.Mina());
          this.p.launch = this.p.launchDelay + this.p.launchRandom * Math.random();
        }else if(struct===2){
          this.struct2();
          this.stage.insert(new Q.Mina());
          this.p.launch = this.p.launchDelay + this.p.launchRandom * Math.random();
        }
      }
    }
  }

});

//Escena de final de juego con dos botones (Play again) y (Exit).
//Cuando se pulsa Exit se envía la máxima puntuación de la sesión al SW del ranking y si es superior la guarda en la BBDD.
  Q.scene('endGame',function(stage) {
    var box = stage.insert(new Q.UI.Container({
      x: Q.width/2, y: Q.height/2, fill: "rgba(0, 95, 154, 0.5)"
    }));
    
    //Actualización de la distancia máxima de la sesión y las monedas.
    if(Q.state.get("score") > DatosLocal.distanciaMaxima) 
      DatosLocal.distanciaMaxima = Q.state.get("score");
    if(Q.state.get("coins") > DatosLocal.numMonedas)
      DatosLocal.numMonedas = Q.state.get("coins");


    var buttonPlay = box.insert(new Q.UI.Button({ x: 0, y: -65, fill: "#CCCCCC",
                                            fill: "rgba(0, 95, 154, 0)",
                                             asset: "muerto.png" }))  

    var buttonPlay = box.insert(new Q.UI.Button({ x: 0, y: 0, fill: "#CCCCCC",
                                             label: "Jugar" }))  
    var buttonExit = box.insert(new Q.UI.Button({ x: 0, y: 50, fill: "#CCCCCC",
                                             label: " Salir " }))         
    var label = box.insert(new Q.UI.Text({x:0, y: -90 - buttonExit.p.h, 
                                          label: stage.options.label }));
    buttonPlay.on("click",function(){
      Q.clearStages();
      Q.stageScene('level1');
      Q.stageScene("HUD", 2);
      Q.stageScene("HUDCoins", 3);
    });
    buttonExit.on("click", function(){
      //////////////////////////////////ACTUALIZAR DISTANCIA MÁXIMA//////////////////////////
      DatosNativos.maxPuntuacion(DatosLocal.distanciaMaxima);
      Q.clearStages();
    })
    box.fit(20);
  });

//Escena principal del juego. Consta de el fondo, un spawner de estructuras y powerups y el personaje.
Q.scene("level1",function(stage) {

  stage.insert(new Q.Repeater({ asset: "fondo.jpg",
                                speedX: 0.5,
                                repeatY: false,
                                y: 90           
                                }));
  stage.insert(new Q.BoxThrower());

  stage.insert(new Q.Player());
  stage.add("viewport");
});
  

//Se cargan todas las imágenes y json con la información de las animaciones.
Q.load("fuego.json, fuego.png, fondo.jpg, agachado.png, agachado.json,  muerto.png, coin.png, coin.json, escudo.png, escudo.json, ameba.png, ameba.json, cajaRota.png, cajaRota.json, mina.png, mina.json, eater.json, eater.png, player.json, player.png, background-wall.png, crates.png, crates.json, cajaBuena.png, cajaBuena.json", 
  function() {
    Q.compileSheets("player.png","player.json");
    Q.compileSheets("crates.png","crates.json");
    Q.compileSheets("cajaBuena.png","cajaBuena.json");
    Q.compileSheets("cajaRota.png","cajaRota.json");
    Q.compileSheets("eater.png", "eater.json");
    Q.compileSheets("mina.png", "mina.json");
    Q.compileSheets("ameba.png", "ameba.json");
    Q.compileSheets("escudo.png", "escudo.json");
    Q.compileSheets("coin.png", "coin.json");
    Q.compileSheets("agachado.png", "agachado.json");
    Q.compileSheets("fuego.png", "fuego.json");

    Q.animations("fuego", {
      fuego: {frames: [0, 1], rate: 1/4, loop: true }
    });

    Q.animations("eater", {
      walk: { frames: [0,1,2,1], rate: 1/5, flip: false, loop: true },
      eat: { frames: [3,4,5], rate: 1/4, flip: false, next: "walk" },
      jump_up: { frames: [6] },
      jump_down: { frames: [7] },
      down: { frames: [8] },
    });

    Q.animations("agachado", {
      agachado: { frames: [0] },
    })
    Q.animations("escudo", {
      escudo: { frames: [0] }
    });

    Q.animations("ameba", {
      ameba: { frames: [0,1,2,3], rate: 1/4, flip: false,  loop: true }
    });

    Q.animations("player", {
      walk_right: { frames: [0,1,2,3,4,5,6,7,8,9,10], rate: 1/15, flip: false, loop: true },
      jump_right: { frames: [13], rate: 1/10, flip: false },
      stand_right: { frames:[14], rate: 1/10, flip: false },
      duck_right: { frames: [15], rate: 1/10, flip: false },
    });
    Q.stageScene("level1");
    Q.stageScene("HUD", 2);
    Q.stageScene("HUDCoins", 3);
  
});


});
