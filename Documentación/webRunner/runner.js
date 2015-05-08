var myApp = (function(){
  var foo = 'Hello World';
  var idCaja = 1;

  return{
    public_property : "hello World publico",
    contCaja : idCaja,
    incrementCaja : function(){
      idCaja = idCaja + 1;
    }
  }

})();
var iddCaja = 0;
var itPlayer = 0;

window.addEventListener("load",function() {

var Q = window.Q = Quintus()
        .include("Sprites, Scenes, Input, 2D, Anim, Touch, UI")
        .setup({ width: 683, height: 384, scaleToFit: true })
        .controls().touch();

  Q.UI.Text.extend("Score",{
    init:function(p){
      this._super({label:"Cajas: 0",x:620,y:0});
      Q.state.on("change.score",this,"score");
    },
    score: function(score){
      this.p.label="Cajas: " + score;
    }
  });

  Q.scene("HUD",function(stage){
    var container = stage.insert(new Q.UI.Container({
      x:0,y:0
    }));
    var label= container.insert(new Q.Score());
    Q.state.set("score",0);
  })

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
      y: 555,
      standingPoints: [ [ -16, 44], [ -23, 35 ], [-23,-48], [23,-48], [23, 35 ], [ 16, 44 ]],
      duckingPoints : [ [ -16, 44], [ -23, 35 ], [-23,-10], [23,-10], [23, 35 ], [ 16, 44 ]],
      speed: 500,
      jump: -600
    });

    this.p.points = this.p.standingPoints;
    this.add("2d, animation");
    this.play("walk");
  },

  step: function(dt) {
   // console.log("player: iteracion-> " + itPlayer + " x-> " + this.p.x + " y-> " + this.p.y );

    this.p.vx += (this.p.speed - this.p.vx)/4;

    if(this.p.y > 555) {
      this.p.y = 555;
      this.p.landed = 1;
      this.p.vy = 0;
    } else {
      this.p.landed = 0;
    }

    if(this.p.doubleJump && this.p.releaseJump)
      if(Q.inputs['up']){
        console.log("doble");
        this.p.vy = this.p.jump;
        this.p.doubleJump = false; 
      }

    if(Q.inputs['up'] && this.p.landed > 0) {
        this.p.vy = this.p.jump;
    }

    this.p.points = this.p.standingPoints;
    if(this.p.landed) {
      this.p.doubleJump = true;
      this.p.releaseJump = false;
      if(Q.inputs['down'] && this.p.animation != "eat") { 
        this.play("down");
        this.p.points = this.p.duckingPoints;
      } else {
        if(this.p.animation != "eat")
        this.play("walk");
      }
    } else {
      if(!Q.inputs['up'])
        this.p.releaseJump = true;  
      //this.play("jump_right");
    }

    this.stage.viewport.centerOn(this.p.x + 175, 500 );

  }
});

Q.component("defaultEnemy", {
  added: function(){
      var entity = this.entity;

  }
});

Q.Sprite.extend("Box",{
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
    this.p.type = 0;
    this.p.collisionMask = Q.SPRITE_NONE;
    this.p.vx = 200;
    this.p.ay = 400;
    this.p.vy = -300;
    this.p.opacity = 0.5;
  }
});



Q.Sprite.extend("BoxGood",{
  init: function() {

    var levels = [ 565, 500, 450 ];

    var player = Q("Player").first();
    this._super({
      touched: false,
      x: player.p.x + Q.width + 50,
      y: levels[Math.floor(Math.random() * 3)],
      //frame: Math.random() < 0.5 ? 1 : 0,
      scale: 0.9,
      type: SPRITE_BOX,
      sheet: "cajaBuena",
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
      Q.state.inc("score", 1);
      this.p.touched = true; 
      playerObj.play("eat");
      var playerVel = playerObj.p.vx;
      //console.log("caja: x-> " + this.p.x + " y-> " + this.p.y + " |  Pj: x-> " + collision.obj.p.x + " y-> " + collision.obj.p.y);
      this.animate({y: playerObj.p.y, vx: playerVel + playerVel/2, opacity: 0, scale: 0.1}, 1/5, {callback: function(){this.destroy();}});
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




Q.GameObject.extend("BoxThrower",{
  init: function() {
    this.p = {
      launchDelay: 0.75,
      launchRandom: 1,
      launch: 2
    }
  },

  update: function(dt) {
    this.p.launch -= dt;

    if(this.p.launch < 0) {

      if(Math.floor(Math.random()*2)===1){
        this.stage.insert(new Q.Box());
        this.p.launch = this.p.launchDelay + this.p.launchRandom * Math.random();
      }else{
        this.stage.insert(new Q.BoxGood());
        this.p.launch = this.p.launchDelay + this.p.launchRandom * Math.random();   
      }
    }
  }

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
  stage.add("viewport");

});
  
Q.load("mina.png, mina.json, eater.json, eater.png, player.json, player.png, background-wall.png, suelo.png, crates.png, crates.json, cajaBuena.png, cajaBuena.json", function() {
    Q.compileSheets("player.png","player.json");
    Q.compileSheets("crates.png","crates.json");
    Q.compileSheets("cajaBuena.png","cajaBuena.json");
    Q.compileSheets("eater.png", "eater.json");
    Q.compileSheets("mina.png", "mina.json");
    Q.animations("eater", {
      walk: { frames: [0,1], rate: 1/4, flip: false, loop: true },
      eat: { frames: [2,3,4], rate: 1/4, flip: false, next: "walk" },
      down: { frames: [5] },
    });
    Q.animations("player", {
      walk_right: { frames: [0,1,2,3,4,5,6,7,8,9,10], rate: 1/15, flip: false, loop: true },
      jump_right: { frames: [13], rate: 1/10, flip: false },
      stand_right: { frames:[14], rate: 1/10, flip: false },
      duck_right: { frames: [15], rate: 1/10, flip: false },
    });
    Q.stageScene("level1");
    Q.stageScene("HUD", 1);
  
});


});
