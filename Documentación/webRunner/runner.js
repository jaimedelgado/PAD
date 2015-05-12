var infoNativo = (function(){
  var foo = 'Hello World';
  var idCaja = 1;

  return{
    coins : 151,
    contCaja : idCaja,
    incrementCaja : function(){
      idCaja = idCaja + 1;
    }
  }

})();

window.addEventListener("load",function() {

var Q = window.Q = Quintus()
        .include("Sprites, Scenes, Input, 2D, Anim, Touch, UI")
        .setup({ width: 683, height: 384, scaleToFit: true })
        .controls().touch();
/////////////////////////////////DISTANCIA///////////////////////////////////////////
  Q.UI.Text.extend("Score",{
    init:function(p){
      this._super({label:"Distance: 0m",x:600,y:0});
      Q.state.on("change.score",this,"score");
    },
    score: function(score){
      this.p.label="Distance: " + score + "m";
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

  Q.UI.Text.extend("Coins",{
    init:function(p){
      this._super({label:"Coins: " + infoNativo.coins,x:615,y:30});
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
    Q.state.set("coins",infoNativo.coins);
  });
var SPRITE_BOX = 1;

Q.gravityY = 2000;

Q.Sprite.extend("Player",{

  init: function(p) {

    this._super(p,{
      sheet: "eater",
      sprite: "eater",
      collisionMask: SPRITE_BOX,
      doubleJump: true,
      releaseJump: false,
      x: 40,
      shield: false,
      y: 559,
      standingPoints: [ [ -16, 44], [ -23, 35 ], [-23,-48], [23,-48], [23, 35 ], [ 16, 44 ]],
      duckingPoints : [ [ -16, 44], [ -23, 35 ], [-23,-10], [23,-10], [23, 35 ], [ 16, 44 ]],
      speed: 500,
      jump: -600,
      stopped: false,
      distance: 0,
    });

    this.p.points = this.p.standingPoints;
    this.add("2d, animation");
    this.play("walk");
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
   
    this.p.vx += (this.p.speed - this.p.vx)/4;
    console.log(this.p.distance);
    if(this.p.speed - this.p.vx < 3 ){
      this.p.distance += 1;
      if(this.p.distance == 100){
        this.p.distance = 0;7
        Q.state.inc("score", 1);
        this.p.stopped = false;
      }
    }else{
      this.p.stopped = true;
    }

    if(this.p.vy == 0)
      console.log("aire");

    if(this.p.y > 559 || this.p.vy == 0 ) {
      if(this.p.y > 559)
        this.p.y = 559; 
      this.p.landed = 1;
      this.p.vy = 0;
    } else {
      this.p.landed = 0;
    }

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
      if(Q.inputs['down'] && this.p.animation != "eat") {
        this.p.w = 98;
        console.log(this);
        this.play("down");
        this.p.points = this.p.duckingPoints;
      } else {
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
      //this.play("jump");
    }

    this.stage.viewport.centerOn(this.p.x + 175, 500 );

  }
});

Q.component("defaultEnemy", {
  added: function(){
      var entity = this.entity;

  }
});

Q.Sprite.extend("Mina",{
  init: function() {
    var levels = [  540,  450 ];
    //var levels = [ 565, 540, 500, 450 ];
    var player = Q("Player").first();
    this._super({
      x: player.p.x + Q.width + 50,
      y: levels[Math.floor(Math.random() * 3)],
      frame: Math.random() < 0.5 ? 0 : 0,//<------------------
      scale: 1.2,
      type: SPRITE_BOX,
      sheet: "mina",
      vx: -100 + 200 * Math.random(),
      vy: 0,
      ay: 0,
      theta: (300 * Math.random() + 200) * (Math.random() < 0.5 ? 1 : -1)
    });


    this.on("hit");
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
  hit: function() {
    if(this.p.type != 0){
      this.p.type = 0;
      console.log("colision");
      this.p.collisionMask = Q.SPRITE_NONE;

      this.p.opacity = 0.5;
    }
      this.p.vx = 200;
      this.p.ay = 400;
      this.p.vy = -300;
  }
});



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
      vx: -200 + 200 * Math.random(),
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
    }else{
    }

     /*  
    this.p.type = 0;
    this.p.collisionMask = Q.SPRITE_NONE;
    this.p.vx = 200;
    this.p.ay = 400;
    this.p.vy = -300;
    this.p.opacity = 1;
  */
  }
  

});



Q.Sprite.extend("BoxFija",{
  init: function(tipo) {

    var levels = [ 573 , 515 , 509 ];

    var player = Q("Player").first();
    this._super({
      touched: false,
      x: player.p.x + Q.width + 50,
      y: tipo == "doble" ? 517  : 573,
      //frame: Math.random() < 0.5 ? 1 : 0,
      scale: 0.9,
      type: SPRITE_BOX,
      sheet: "cajaBuena",
      vx: 50,
      vy: 0,
      ay: 0,
    });

    this.add("animation, tween");

    this.on("hit", this, "sensor");

    //this.add("animation, tween");
  },

  step: function(dt) {
    this.p.x += this.p.vx * dt;


    this.p.vy += this.p.ay * dt;
    this.p.y += this.p.vy * dt;
    
    if(this.p.y > 800) { this.destroy(); }

  },

  sensor: function(collision) {
    /*var playerObj = collision.obj;
    if(!this.p.touched && playerObj.isA("Player")){
      Q.state.inc("score", 1);
      this.p.touched = true; 
      playerObj.play("eat");
      var playerVel = playerObj.p.vx;
      //console.log("caja: x-> " + this.p.x + " y-> " + this.p.y + " |  Pj: x-> " + collision.obj.p.x + " y-> " + collision.obj.p.y);
      this.animate({y: playerObj.p.y, vx: playerVel + playerVel/2, opacity: 0, scale: 0.1}, 1/5, {callback: function(){this.destroy();}});
    }else{
    }

      
    this.p.type = 0;
    this.p.collisionMask = Q.SPRITE_NONE;
    this.p.vx = 200;
    this.p.ay = 400;
    this.p.vy = -300;
    this.p.opacity = 1;
  */
  }
  

});



Q.Sprite.extend("Ameba",{
  init: function(level) {

    var levels = [ 573 , 515 , 509 ];

    var player = Q("Player").first();
    this._super({
      player: Q("Player").first(),
      x: player.p.x,
      //y: tipo == "doble" ? 515  : 573,
      y: player.p.y,
      //frame: Math.random() < 0.5 ? 1 : 0,
      scale: 1.3,
      type: 0,
      opacity: 0.4,
      sheet: "ameba",
      sprite: "ameba",
      timeStart: 0,
      timeMax: level > 0 ? 5*level : 5,
      vx: player.p.vx,
      vy: player.p.vy,
      ay: player.p.ay,
    });

    this.add("animation, tween");
    
    this.play("ameba");

    this.on("hit", this, "sensor");

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
    /*var playerObj = collision.obj;
    if(!this.p.touched && playerObj.isA("Player")){
      Q.state.inc("score", 1);
      this.p.touched = true; 
      playerObj.play("eat");
      var playerVel = playerObj.p.vx;
      //console.log("caja: x-> " + this.p.x + " y-> " + this.p.y + " |  Pj: x-> " + collision.obj.p.x + " y-> " + collision.obj.p.y);
      this.animate({y: playerObj.p.y, vx: playerVel + playerVel/2, opacity: 0, scale: 0.1}, 1/5, {callback: function(){this.destroy();}});
    }else{
    }

      
    this.p.type = 0;
    this.p.collisionMask = Q.SPRITE_NONE;
    this.p.vx = 200;
    this.p.ay = 400;
    this.p.vy = -300;
    this.p.opacity = 1;
  */
  }
  

});

Q.Sprite.extend("Escudo",{
  init: function(tipo) {

    var levels = [ 573 , 515 , 509 ];

    var player = Q("Player").first();
    this._super({
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
    
    if(this.p.y > 800) { this.destroy(); }

  },

  sensor: function(collision) {
    var playerObj = collision.obj;
    if(!this.p.touched && playerObj.isA("Player")){
      this.p.touched = true;
      if(!playerObj.p.shield){
        this.stage.insert(new Q.Ameba());
        playerObj.setShield(true);
      }
      //console.log("caja: x-> " + this.p.x + " y-> " + this.p.y + " |  Pj: x-> " + collision.obj.p.x + " y-> " + collision.obj.p.y);
      this.animate({y: this.p.y - 30}, 1/5, {callback: function(){this.destroy();}});
    }else{
    }

      /*
    this.p.type = 0;
    this.p.collisionMask = Q.SPRITE_NONE;
    this.p.vx = 200;
    this.p.ay = 400;
    this.p.vy = -300;
    this.p.opacity = 1;
  */
  }
  

});



//////////////////////////////COIN////////////////////////////////


Q.Sprite.extend("Coin",{
  init: function(tipo) {

    var levels = [ 573 , 515 , 509 ];

    var player = Q("Player").first();
    this._super({
      touched: false,
      x: player.p.x + Q.width + 50,
      y: 509,
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
    
    if(this.p.y > 800) { this.destroy(); }

  },

  animateCoin: function(){
    this
      .animate({scale: 0.5}, 1 )
      .chain({scale: 0.9}, 1, {
        callback: function(){this.animateCoin();}});
  },

  sensor: function(collision) {
    var playerObj = collision.obj;
    if(!this.p.touched && playerObj.isA("Player")){
      this.p.touched = true;

      
      Q.state.inc("coins", 1);
      //console.log("caja: x-> " + this.p.x + " y-> " + this.p.y + " |  Pj: x-> " + collision.obj.p.x + " y-> " + collision.obj.p.y);
      this.animate({y: this.p.y - 30}, 1/5, {callback: function(){this.destroy();}});
    }else{
    }

      /*
    this.p.type = 0;
    this.p.collisionMask = Q.SPRITE_NONE;
    this.p.vx = 200;
    this.p.ay = 400;
    this.p.vy = -300;
    this.p.opacity = 1;
  */
  }
  

});

///////////////////////////////////////////////////////////////



Q.GameObject.extend("BoxThrower",{
  init: function() {
    this.p = {
      player: null,
      launchDelay: 0.75,
      launchRandom: 1,
      launch: 2
    }
  },

  update: function(dt) {
    if(this.p.player==null)
      this.p.player=Q("Player").first();

    if(this.p.player && !this.p.player.stopped()){
      this.p.launch -= dt;


      if(this.p.launch < 0 ) {
          
        if(Math.floor(Math.random()*2)===1){
          this.stage.insert(new Q.BoxFija("doble"));
          this.stage.insert(new Q.BoxFija());
          this.p.launch = this.p.launchDelay + this.p.launchRandom * Math.random();
        }else{
          this.stage.insert(new Q.Mina());
          this.p.launch = this.p.launchDelay + this.p.launchRandom * Math.random();
        }
          /*
        }
        if(Math.floor(Math.random()*2)===1){
          this.stage.insert(new Q.BoxFija());
          this.p.launch = this.p.launchDelay + this.p.launchRandom * Math.random();
        }else{
          this.stage.insert(new Q.BoxGood());
          this.p.launch = this.p.launchDelay + this.p.launchRandom * Math.random();   
        }*/
      }
    }
  }

});


  Q.scene('endGame',function(stage) {
    var box = stage.insert(new Q.UI.Container({
      x: Q.width/2, y: Q.height/2, fill: "rgba(0,0,0,0.5)"
    }));
    
    var buttonPlay = box.insert(new Q.UI.Button({ x: 0, y: 0, fill: "#CCCCCC",
                                             label: "Play Again" }))  
    var buttonExit = box.insert(new Q.UI.Button({ x: 0, y: 0, fill: "#CCCCCC",
                                             label: "Play Again" }))         
    var label = box.insert(new Q.UI.Text({x:10, y: -10 - button.p.h, 
                                          label: stage.options.label }));
    buttonPlay.on("click",function() {
      Q.audio.stop();
      Q.clearStages();
      Q.stageScene('level1');
    });
    buttonExit.on("click", function(){
      Q.clearStages();
    })
    box.fit(20);
  });


Q.scene("level1",function(stage) {

  stage.insert(new Q.Repeater({ asset: "background-wall.png",
                                speedX: 0.5 }));

  stage.insert(new Q.Repeater({ asset: "suelo.png",
                                repeatY: false,
                                speedX: 1.0,
                                y: 300 }));

  stage.insert(new Q.BoxThrower());

  stage.insert(new Q.Player());
  //stage.insert(new Q.Ameba());
  stage.add("viewport");

});
  
Q.load("coin.png, coin.json, escudo.png, escudo.json, ameba.png, ameba.json, cajaRota.png, cajaRota.json, mina.png, mina.json, eater.json, eater.png, player.json, player.png, background-wall.png, suelo.png, crates.png, crates.json, cajaBuena.png, cajaBuena.json", function() {
    Q.compileSheets("player.png","player.json");
    Q.compileSheets("crates.png","crates.json");
    Q.compileSheets("cajaBuena.png","cajaBuena.json");
    Q.compileSheets("cajaRota.png","cajaRota.json");
    Q.compileSheets("eater.png", "eater.json");
    Q.compileSheets("mina.png", "mina.json");
    Q.compileSheets("ameba.png", "ameba.json");
    Q.compileSheets("escudo.png", "escudo.json");
    Q.compileSheets("coin.png", "coin.json");

    Q.animations("eater", {
      walk: { frames: [0,1], rate: 1/4, flip: false, loop: true },
      eat: { frames: [2,3,4], rate: 1/4, flip: false, next: "walk" },
      jump_up: { frames: [5] },
      jump_down: { frames: [6] },
      down: { frames: [7] },
    });

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
    Q.stageScene("HUD", 1);
    Q.stageScene("HUDCoins", 2);
  
});


});
