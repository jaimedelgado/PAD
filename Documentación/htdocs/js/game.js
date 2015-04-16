function initGame() 
{
   document.getElementById('musica').play();
   console.log("entra en juego!");
   myListener = function () {};
   myListener.prototype.BeginContact = function (contact) {
      console.log("begin contcat");
      var shapeA = contact.GetFixtureA().GetShape();
      var shapeB = contact.GetFixtureB().GetShape();

      if(shapeA.my_type === "yo" || shapeB.my_type == "yo")
      {
         if(shapeA.my_type !== shapeB.my_type) console.log("game over");
          parar();
      }

   };
   myListener.prototype.EndContact = function (contact) {
      console.log("end contcat");      
   };
   myListener.prototype.PreSolve = function (contact, oldManifold) {};
   myListener.prototype.PostSolve = function (contact, impulse) {};

  
   var game_on = false;
   var   b2Vec2 = Box2D.Common.Math.b2Vec2
      ,  b2AABB = Box2D.Collision.b2AABB
      ,  b2BodyDef = Box2D.Dynamics.b2BodyDef         
      ,  b2Body = Box2D.Dynamics.b2Body
      ,  b2FixtureDef = Box2D.Dynamics.b2FixtureDef
      ,  b2Fixture = Box2D.Dynamics.b2Fixture
      ,  b2World = Box2D.Dynamics.b2World
      ,  b2MassData = Box2D.Collision.Shapes.b2MassData
      ,  b2PolygonShape = Box2D.Collision.Shapes.b2PolygonShape
      ,  b2CircleShape = Box2D.Collision.Shapes.b2CircleShape
      ,  b2DebugDraw = Box2D.Dynamics.b2DebugDraw
      ,  b2MouseJointDef =  Box2D.Dynamics.Joints.b2MouseJointDef
      ,  b2ContactListener = myListener
      ;
   
   Box2D.Dynamics.b2ContactListener.b2_defaultListener = new myListener;

   var world = new b2World(
         new b2Vec2(0, 0)    //gravity
      ,  true                 //allow sleep
   );
   


  
   jugar();



function jugar(){
   
      nIntervId = setInterval(creaEnemigos, 2000);

}




   var fixDef = new b2FixtureDef;
   fixDef.density = 0.5;
   fixDef.friction = 0.0;
   fixDef.restitution = 1.0;
   
   var bodyDef = new b2BodyDef;

//function paredes() {
   //create ground
   bodyDef.type = b2Body.b2_staticBody;
   fixDef.shape = new b2PolygonShape;
   fixDef.shape.SetAsBox(15, 0.05);
   bodyDef.position.Set(15, 0);
   world.CreateBody(bodyDef).CreateFixture(fixDef);
   //bodyDef.position.Set(25, 50);
   //world.CreateBody(bodyDef).CreateFixture(fixDef);
   fixDef.shape.SetAsBox(0.05, 18);
   bodyDef.position.Set(0, 18);
   world.CreateBody(bodyDef).CreateFixture(fixDef);
   bodyDef.position.Set(30, 15);
   world.CreateBody(bodyDef).CreateFixture(fixDef);
//}
   //create some objects
function creaEnemigos() {
   bodyDef.type = b2Body.b2_dynamicBody;
   rangoMaxMirandom = 6;
   rangoMinMirandom = 3;
   mirandom = Math.floor(Math.random()*(rangoMaxMirandom-(rangoMinMirandom-1))) + rangoMinMirandom;
   for(var i = 0; i < mirandom; ++i) { //aqui meter un random para que cree de 1 a 3 circulos(agujas)
      
   rangoXa = 10;
   rangoXb = -10;
      vX = Math.floor(Math.random()*(rangoXa-(rangoXb-10))) + rangoXb;
   rangoYa = 35; 
   rangoYb = 10;
      vY = Math.floor(Math.random()*(rangoYa-(rangoYb-10))) + rangoYb;
   radioEnemigos = 0.2; //delimita el radio de la punta de la aguja, 2px
      fixDef.shape = new b2CircleShape(radioEnemigos);
      bodyDef.position.x = Math.ceil(Math.random()*29);
      bodyDef.position.y = 1;
      var ball = world.CreateBody(bodyDef);
      var fixture = ball.CreateFixture(fixDef);
      fixture.GetShape().my_type = "enemigo";         

      ball.SetLinearVelocity(new b2Vec2(vX,vY)) // Establecemos la velocidad con la que saldrÃ¡ la bola

   } 
}


//function yo() {
   bodyDef.type = b2Body.b2_dynamicBody;
   fixDef.shape = new b2CircleShape(3.2);
   fixDef.shape.my_type = "yo";         

   b2MassData(new b2Vec2(0,0), 0, 0);

   bodyDef.position.x = 15;
   bodyDef.position.y = 32;

   var fixture = world.CreateBody(bodyDef).CreateFixture(fixDef);
   fixture.GetShape().my_type = "yo";         


    //}




function parar(){
   if (game_on) {
      var vecGravity = new b2Vec2(0,50);
      world.m_gravity = vecGravity;
      clearInterval(nIntervId);
      document.getElementById('musica').pause();
      alert('Game Over');
   };
   game_on = false;
}
   
function setGameContext(){
          yo();
          paredes();
   }
   //setup debug draw
   var debugDraw = new b2DebugDraw();
	debugDraw.SetSprite(document.getElementById("canvas").getContext("2d"));
	debugDraw.SetDrawScale(10.0);
	debugDraw.SetFillAlpha(0.5);
	debugDraw.SetLineThickness(1.0);
	debugDraw.SetFlags(b2DebugDraw.e_shapeBit | b2DebugDraw.e_jointBit);
	world.SetDebugDraw(debugDraw);
  
   window.setInterval(update, 1000 / 50);
   /*
   var img = new Image();
   img.src = "./images/needle.png";
   console.log(debugDraw);
   debugDraw.m_ctx.drawImage(img, 5, 5);//mouse
   */

   
   var mouseX, mouseY, mousePVec, isMouseDown, selectedBody, mouseJoint;
   var canvasPosition = getElementPosition(document.getElementById("canvas"));
   
   document.addEventListener("touchstart", function(e) {
      isMouseDown = true;
      handleMouseMove(e);
      document.addEventListener("touchmove", handleMouseMove, true);
   }, true);
   
   document.addEventListener("touchend", function() {
      document.removeEventListener("touchmove", handleMouseMove, true);
      isMouseDown = false;
      mouseX = undefined;
      mouseY = undefined;
   }, true);

   function handleMouseMove(e) {
      mouseX = (e.changedTouches[0].clientX - canvasPosition.x) / 10;
      mouseY = (e.changedTouches[0].clientY - canvasPosition.y) / 10;
   };
   
   function getBodyAtMouse() {
      mousePVec = new b2Vec2(mouseX, mouseY);
      var aabb = new b2AABB();
      aabb.lowerBound.Set(mouseX - 0.001, mouseY - 0.001);
      aabb.upperBound.Set(mouseX + 0.001, mouseY + 0.001);
      
      // Query the world for overlapping shapes.

      selectedBody = null;
      world.QueryAABB(getBodyCB, aabb);
      return selectedBody;
   }

   function getBodyCB(fixture) {
      if(fixture.GetBody().GetType() != b2Body.b2_staticBody) {
         if(fixture.GetShape().TestPoint(fixture.GetBody().GetTransform(), mousePVec)) {
            selectedBody = fixture.GetBody();
            return false;
         }
      }
      return true;
   }

   //$(document).on('click', '#parar',function(){
     // parar();
   //});
   //update
   
   function update() {

          if(isMouseDown && (!mouseJoint)) {
               var body = getBodyAtMouse();
               if(body) {
                  var md = new b2MouseJointDef();
                  md.bodyA = world.GetGroundBody();
                  md.bodyB = body;
                  md.target.Set(mouseX, mouseY);
                  md.collideConnected = true;
                 // md.maxForce = 10.0 * body.GetMass();
                   md.maxForce = 100000000;
                  mouseJoint = world.CreateJoint(md);
                  body.SetAwake(true);
                 game_on = true;
               }
            }
            
            if(mouseJoint) {
               if(isMouseDown) {
                  mouseJoint.SetTarget(new b2Vec2(mouseX, mouseY));
               } else {
                  world.DestroyJoint(mouseJoint);
                  mouseJoint = null;
               }
            }
         
      world.Step(1 / 60, 10, 10);
      world.DrawDebugData();
      world.ClearForces();
   };
   
   //helpers
   
   //http://js-tut.aardon.de/js-tut/tutorial/position.html
   function getElementPosition(element) {
      var elem=element, tagname="", x=0, y=0;
     
      while((typeof(elem) == "object") && (typeof(elem.tagName) != "undefined")) {
         y += elem.offsetTop;
         x += elem.offsetLeft;
         tagname = elem.tagName.toUpperCase();

         if(tagname == "BODY")
            elem=0;

         if(typeof(elem) == "object") {
            if(typeof(elem.offsetParent) == "object")
               elem = elem.offsetParent;
         }
      }

      return {x: x, y: y};
   }

function hayColision (element1, element2) {
element1.addEventListener()

}

};
