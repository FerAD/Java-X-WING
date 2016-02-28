
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Point3D {
   public float x, y, z;
   public Point3D( float X, float Y, float Z ) {
      x = X;  y = Y;  z = Z;
   }
}

class Edge {
   public int a, b;
   public Edge( int A, int B ) {
      a = A;  b = B;
   }
}

public class WireframeJApplet extends JApplet
                  implements KeyListener, FocusListener, MouseListener {

   int width, height;
   // int mx, my;  // the most recently recorded mouse coordinates

   int numVertices = 13264;
   int numEdges = 14893;

   int azimuth = 35, elevation = 30;

   Point3D[] vertices;
   Edge[] edges;

   boolean focussed = false;   // True when this applet has input focus.

   DisplayPanel canvas;

   public void init() {
      //valores para cilindros
      int cv = 100; //vertices de cada circulo de los cilindros
      int ch = 2;
      int r = 1; // radio de los cilindros
      float zcv = 0.0f;
      double alfa; //angulo
      //**********************
      
      vertices = new Point3D[numVertices];   //defino el tamaño de los vertices
      edges = new Edge[numEdges];   //defino el tamaño de las aristas

      //*************Parte frontal de la nave*************
      float x = -2.0f,y = 0.0f, z = 0.0f; // defino x y z 
      float x2,y2;   // defino x y secundarios
      for(int i = 0;  i<numVertices-2; i++){ //recorro todos los vertices

         if(i<6){ //saco z para cada hectagono que forma la parte frontal
            z = 0.0f;
         }else if(i<12 && i>5){
            z = 3.0f;
         }else if(i<18 && i>11){
            z = 5.0f;
         }else if(i<24 && i>17){
            z = 8.0f;
         }else if(i<30 && i>23){
            z = 12.0f;
         }else if(i<32 && i>29){
            z = 12.0f;
         }

         if(i<18){   // calculo el vertice de cada hectagono dependiendo el modulo
            if((i % 6) == 0){
               x2 = x;
               y2 = y;
               vertices[i] = new Point3D(x2,y2,z);
            }if((i % 6) == 1){
               x2 = x/2;
               y2 = -1*x;
               vertices[i] = new Point3D(x2,y2,z);
            }if((i % 6 == 2)){
               x2 = (-1*x)/2;
               y2 = -1*x;
               vertices[i] = new Point3D(x2,y2,z);
            }
            if((i % 6) == 3){
               x2 = -1*x;
               y2 = 0;
               vertices[i] = new Point3D(x2,y2,z);
            }
            if((i % 6) == 4){
               x2 = (-1*x)/2;
               y2 = x;
               vertices[i] = new Point3D(x2,y2,z);
            }
            if((i % 6) == 5){
               x2 = x/2;
               y2 = x;
               vertices[i] = new Point3D(x2,y2,z);
            }
         }else{
            if((i % 6) == 0){
               x2 = x/2;
               x = x2;
               y2 = 0;
               vertices[i] = new Point3D(x2,y2,z);
            }if((i % 6) == 1){
               x2 = x/2;
               y2 = -1*x;
               vertices[i] = new Point3D(x2,y2,z);
            }if((i % 6 == 2)){
               x2 = (-1*x)/2;
               y2 = -1*x;
               vertices[i] = new Point3D(x2,y2,z);
            }
            if((i % 6) == 3){
               x2 = -1*x;
               y2 = 0;
               vertices[i] = new Point3D(x2,y2,z);
            }
            if((i % 6) == 4){
               x2 = (-1*x)/2;
               y2 = x;
               vertices[i] = new Point3D(x2,y2,z);
            }
            if((i % 6) == 5){
               x2 = x/2;
               y2 = x;
               vertices[i] = new Point3D(x2,y2,z);
            }
         }
      }

      vertices[30] = new Point3D(-.2f, 0f, 13f);   //estos son los puntos mas al frente de la nave que juntan 
      vertices[31] = new Point3D(.2f, 0f, 13f);    //los vertices del ultimo hequilatero
      


      for(int i = 0; i < 24; i++){  //ciclo que crea las aristas que unen los equilateros 
         edges[i] = new Edge( i, i+6 );
      }
      
      edges[24] = new Edge (24,30); //defino las aristas que unen al ultimo equilatero con la punta frontal
      edges[25] = new Edge (25,30);
      edges[26] = new Edge (26,31);
      edges[27] = new Edge (27,31);
      edges[28] = new Edge (28,31);
      edges[29] = new Edge (29,30);
      edges[30] = new Edge (30,31);

      for(int i = 0; i < 30; i++){  //se define la conexion de los vertices de cada hectagono
         if((i%6)==0){
            edges[i+31] = new Edge(i,i+1);
         }else if((i%6)==5){
            edges[i+31] = new Edge(i,i-5);
         }else{
            edges[i+31] = new Edge(i,i+1);
         }

      }
      //***************Termina parte frontal

      //***************Se crean los cilindros****************
      //cada cilindro tiene 200 vertices y 300 aritas

      //primer cilindro arriba a la izquierda
      for(int j = 0; j<ch; j++){
         alfa = 280; //donde comienza a pintar el circulo
         if(j == 0){ //creo el primer circulo
            for(int i = 0; i<cv; i++){ //creo los vertices apartir del ultimo punto del arreglo utilizado
               vertices[i+32] = new Point3D((float)((Math.cos(Math.toRadians(alfa)))*r)-2,(float)((Math.sin(Math.toRadians(alfa)))*r)+2,zcv+5);
               alfa += 360/cv;   // cambia el angulo para pintar el vertice
            }
            for(int i = 0; i<cv; i++){ // creo las aristas que unen vertice del circulo apartir del ultimo punto del arreglo utilizado 
               if(i<99){
                  edges[i+61] = new Edge(i+32,(i+32)+1);
               }else if(i == 99){
                  edges[i+61] = new Edge(i+32,(i+32)-99);
               }
            }   
         }
         alfa = 280; //donde comienza a pintar el circulo
         if(j == 1){ //pinto el otro ciruculo del cilindro de igual manera que el anterior 

            for(int i = 0; i<cv; i++){ //creo los vertices apartir del ultimo putno del arreglo utilizado
               vertices[i+132] = new Point3D((float)((Math.cos(Math.toRadians(alfa)))*r)-2,(float)((Math.sin(Math.toRadians(alfa)))*r)+2,zcv+1);
               alfa += 360/cv;   //cambio el angulo para pintar el vertice
            }
            for(int i = 0; i<cv; i++){ // creo las aristas que unen los vertices de los circulos del cilindro
               if(i<99){
                  edges[i+161] = new Edge(i+132,(i+132)+1);
               }else if(i == 99){
                  edges[i+161] = new Edge(i+132,(i+132)-99);
               }
               edges[i+261] = new Edge( i+32, i+132 );   // pinto todas las aritas que unen a los dos circulos creados
            }   
         }
      }
      //segundo cilindro abajo a la izquierda
      for(int j = 0; j<ch; j++){
         alfa = 130;
         if(j == 0){ 
            for(int i = 0; i<cv; i++){
               vertices[i+232] = new Point3D((float)((Math.cos(Math.toRadians(alfa)))*r)-2,(float)((Math.sin(Math.toRadians(alfa)))*r)-2,zcv+5);
               alfa += 360/cv;
            }
            for(int i = 0; i<cv; i++){
               if(i<99){
                  edges[i+361] = new Edge(i+232,(i+232)+1);
               }else if(i == 99){
                  edges[i+361] = new Edge(i+232,(i+232)-99);
               }
            }   
         }
         alfa = 130;
         if(j == 1){
            for(int i = 0; i<cv; i++){
               vertices[i+332] = new Point3D((float)((Math.cos(Math.toRadians(alfa)))*r)-2,(float)((Math.sin(Math.toRadians(alfa)))*r)-2,zcv+1);
               alfa += 360/cv;
            }
            for(int i = 0; i<cv; i++){
               if(i<99){
                  edges[i+461] = new Edge(i+332,(i+332)+1);
               }else if(i == 99){
                  edges[i+461] = new Edge(i+332,(i+332)-99);
               }
               edges[i+561] = new Edge( i+232, i+332 );
            }   
         }
      }
      //tercer cilindro abajo a la derecha
      for(int j = 0; j<ch; j++){
         alfa = 100;
         if(j == 0){ 
            for(int i = 0; i<cv; i++){
               vertices[i+432] = new Point3D((float)((Math.cos(Math.toRadians(alfa)))*r)+2,(float)((Math.sin(Math.toRadians(alfa)))*r)-2,zcv+5);
               alfa += 360/cv;
            }
            for(int i = 0; i<cv; i++){
               if(i<99){
                  edges[i+661] = new Edge(i+432,(i+432)+1);
               }else if(i == 99){
                  edges[i+661] = new Edge(i+432,(i+432)-99);
               }
            }   
         }
         alfa = 100;
         if(j == 1){
            for(int i = 0; i<cv; i++){
               vertices[i+532] = new Point3D((float)((Math.cos(Math.toRadians(alfa)))*r)+2,(float)((Math.sin(Math.toRadians(alfa)))*r)-2,zcv+1);
               alfa += 360/cv;
            }
            for(int i = 0; i<cv; i++){
               if(i<99){
                  edges[i+761] = new Edge(i+532,(i+532)+1);
               }else if(i == 99){
                  edges[i+761] = new Edge(i+532,(i+532)-99);
               }
               edges[i+861] = new Edge( i+432, i+532 );
            }   
         }
      }
      //cuarto cilindro arriba a la derecha
      for(int j = 0; j<ch; j++){
         alfa = 320;
         if(j == 0){ 
            for(int i = 0; i<cv; i++){
               vertices[i+632] = new Point3D((float)((Math.cos(Math.toRadians(alfa)))*r)+2,(float)((Math.sin(Math.toRadians(alfa)))*r)+2,zcv+5);
               alfa += 360/cv;
            }
            for(int i = 0; i<cv; i++){
               if(i<99){
                  edges[i+961] = new Edge(i+632,(i+632)+1);
               }else if(i == 99){
                  edges[i+961] = new Edge(i+632,(i+632)-99);
               }
            }   
         }
         alfa = 320;
         if(j == 1){
            for(int i = 0; i<cv; i++){
               vertices[i+732] = new Point3D((float)((Math.cos(Math.toRadians(alfa)))*r)+2,(float)((Math.sin(Math.toRadians(alfa)))*r)+2,zcv+1);
               alfa += 360/cv;
            }
            for(int i = 0; i<cv; i++){
               if(i<99){
                  edges[i+1061] = new Edge(i+732,(i+732)+1);
               }else if(i == 99){
                  edges[i+1061] = new Edge(i+732,(i+732)-99);
               }
               edges[i+1161] = new Edge( i+632, i+732 );
            }   
         }
      }

       //primer cilindro arriba a la izquierda CHICO 
      for(int j = 0; j<ch; j++){
         alfa = 280; //donde comienza a pintar el circulo
         if(j == 0){ //creo el primer circulo
            for(int i = 0; i<cv; i++){ //creo los vertices apartir del ultimo punto del arreglo utilizado
               vertices[i+848] = new Point3D((float)((Math.cos(Math.toRadians(alfa)))*.5)-2,(float)((Math.sin(Math.toRadians(alfa)))*.5)+2,zcv+1);
               alfa += 360/cv;   // cambia el angulo para pintar el vertice
            }
            for(int i = 0; i<cv; i++){ // creo las aristas que unen vertice del circulo apartir del ultimo arreglo utilizado 
               if(i<99){
                  edges[i+1277] = new Edge(i+848,(i+848)+1);
               }else if(i == 99){
                  edges[i+1277] = new Edge(i+848,(i+848)-99);
               }
            }   
         }
         alfa = 280; //donde comienza a pintar el circulo
         if(j == 1){ //pinto el otro ciruculo del cilindro de igual manera que el anterior 

            for(int i = 0; i<cv; i++){
               vertices[i+948] = new Point3D((float)((Math.cos(Math.toRadians(alfa)))*.5)-2,(float)((Math.sin(Math.toRadians(alfa)))*.5)+2,zcv-1);
               alfa += 360/cv;
            }
            for(int i = 0; i<cv; i++){
               if(i<99){
                  edges[i+1377] = new Edge(i+948,(i+948)+1);
               }else if(i == 99){
                  edges[i+1377] = new Edge(i+948,(i+948)-99);
               }
               edges[i+1477] = new Edge( i+848, i+948);   // pinto todas las aritas que unen a los dos circulos creados
            }   
         }
      }

       //segundo cilindro abajo a la izquierda CHICO
      for(int j = 0; j<ch; j++){
         alfa = 280;
         if(j == 0){ 
            for(int i = 0; i<cv; i++){
               vertices[i+1048] = new Point3D((float)((Math.cos(Math.toRadians(alfa)))*.5)-2,(float)((Math.sin(Math.toRadians(alfa)))*.5)-2,zcv+1);
               alfa += 360/cv;
            }
            for(int i = 0; i<cv; i++){
               if(i<99){
                  edges[i+1577] = new Edge(i+1048,(i+1048)+1);
               }else if(i == 99){
                  edges[i+1577] = new Edge(i+1048,(i+1048)-99);
               }
            }   
         }
         alfa = 280;
         if(j == 1){
            for(int i = 0; i<cv; i++){
               vertices[i+1148] = new Point3D((float)((Math.cos(Math.toRadians(alfa)))*.5)-2,(float)((Math.sin(Math.toRadians(alfa)))*.5)-2,zcv-1);
               alfa += 360/cv;
            }
            for(int i = 0; i<cv; i++){
               if(i<99){
                  edges[i+1677] = new Edge(i+1148,(i+1148)+1);
               }else if(i == 99){
                  edges[i+1677] = new Edge(i+1148,(i+1148)-99);
               }
               edges[i+1777] = new Edge( i+1048, i+1148 );
            }   
         }
      }
      //tercer cilindro abajo a la derecha CHICO 
      for(int j = 0; j<ch; j++){
         alfa = 280;
         if(j == 0){ 
            for(int i = 0; i<cv; i++){
               vertices[i+1248] = new Point3D((float)((Math.cos(Math.toRadians(alfa)))*.5)+2,(float)((Math.sin(Math.toRadians(alfa)))*.5)-2,zcv+1);
               alfa += 360/cv;
            }
            for(int i = 0; i<cv; i++){
               if(i<99){
                  edges[i+1877] = new Edge(i+1248,(i+1248)+1);
               }else if(i == 99){
                  edges[i+1877] = new Edge(i+1248,(i+1248)-99);
               }
            }   
         }
         alfa = 280;
         if(j == 1){
            for(int i = 0; i<cv; i++){
               vertices[i+1348] = new Point3D((float)((Math.cos(Math.toRadians(alfa)))*.5)+2,(float)((Math.sin(Math.toRadians(alfa)))*.5)-2,zcv-1);
               alfa += 360/cv;
            }
            for(int i = 0; i<cv; i++){
               if(i<99){
                  edges[i+1977] = new Edge(i+1348,(i+1348)+1);
               }else if(i == 99){
                  edges[i+1977] = new Edge(i+1348,(i+1348)-99);
               }
               edges[i+2077] = new Edge( i+1248, i+1348 );
            }   
         }
      }

      //cuarto cilindro arriba a la derecha CHCIO
      for(int j = 0; j<ch; j++){
         alfa = 320;
         if(j == 0){ 
            for(int i = 0; i<cv; i++){
               vertices[i+1448] = new Point3D((float)((Math.cos(Math.toRadians(alfa)))*.5)+2,(float)((Math.sin(Math.toRadians(alfa)))*.5)+2,zcv+1);
               alfa += 360/cv;
            }
            for(int i = 0; i<cv; i++){
               if(i<99){
                  edges[i+2177] = new Edge(i+1448,(i+1448)+1);
               }else if(i == 99){
                  edges[i+2177] = new Edge(i+1448,(i+1448)-99);
               }
            }   
         }
         alfa = 320;
         if(j == 1){
            for(int i = 0; i<cv; i++){
               vertices[i+1548] = new Point3D((float)((Math.cos(Math.toRadians(alfa)))*.5)+2,(float)((Math.sin(Math.toRadians(alfa)))*.5)+2,zcv-1);
               alfa += 360/cv;
            }
            for(int i = 0; i<cv; i++){
               if(i<99){
                  edges[i+2277] = new Edge(i+1548,(i+1548)+1);
               }else if(i == 99){
                  edges[i+2277] = new Edge(i+1548,(i+1548)-99);
               }
               edges[i+2377] = new Edge( i+1448, i+1548 );
            }   
         }
      }

      //*********************Termina creacion de cilindros

      //*********************Se crean las alas**************************
      // int numVertices = 836;
      //int numEdges = 1268;
      float xa = 0,ya = 0,z1 = 0,xb = 0,yb = 0;
      for(int i = 0;  i<16; i++){
         
         xa = 1.5f;
         ya = 1;
         xb = 10;
         yb = 3;

         if((i%4) == 0){
            z1 = 0;
         }else if((i%4) == 1){
            z1 = 5;
         }else if((i%4) == 2){
            z1 = 1.3f;
         }else if((i%4) == 3){
            z1 = 4.3f;
         }
            
         
         if(i<4){
            if(i<2){ //ala arriba a la izquierda
               vertices[i+832] = new Point3D(xa,ya,z1);
            }else{
               vertices[i+832] = new Point3D(xb,yb,z1);
            }
         }else if(i<8 && i>3){   //ala abajo a la izquierda
            if(i<6){
               vertices[i+832] = new Point3D(xa,-ya,z1);
            }else{
               vertices[i+832] = new Point3D(xb,-yb,z1);
            }
         }else if(i<12 && i>7){  //ala arriba a la derecha
            if(i<10){
               vertices[i+832] = new Point3D(-xa,ya,z1);
            }else{
               vertices[i+832] = new Point3D(-xb,yb,z1);
            }
         }else if(i<16 && i>11){ //ala abajo a la derecha
            if(i<14){
               vertices[i+832] = new Point3D(-xa,-ya,z1);
            }else{
               vertices[i+832] = new Point3D(-xb,-yb,z1);
            }
         }

      }
      //Pinta aristas de las 4 alas
      edges[1261] = new Edge(832,833);
      edges[1262] = new Edge(832,834);
      edges[1263] = new Edge(835,833);
      edges[1264] = new Edge(835,834);

      edges[1265] = new Edge(836,837);
      edges[1266] = new Edge(836,838);
      edges[1267] = new Edge(839,837);
      edges[1268] = new Edge(839,838);

      edges[1269] = new Edge(840,841);
      edges[1270] = new Edge(840,842);
      edges[1271] = new Edge(843,841);
      edges[1272] = new Edge(843,842);

      edges[1273] = new Edge(844,845);
      edges[1274] = new Edge(844,846);
      edges[1275] = new Edge(847,845);
      edges[1276] = new Edge(847,846);

      //int numVertices = 13248;int numEdges = 14877;
      xa = 0;
      ya = 0;
      z1 = 0;
      xb = 0;
      yb = 0;
      for(int i = 0;  i<16; i++){
         
         xa = 1.5f;
         ya = 1.3f;
         xb = 10;
         yb = 3.2f;

         if((i%4) == 0){
            z1 = 0;
         }else if((i%4) == 1){
            z1 = 5;
         }else if((i%4) == 2){
            z1 = 1.3f;
         }else if((i%4) == 3){
            z1 = 4.3f;
         }
            
         
         if(i<4){
            if(i<2){ //ala arriba a la izquierda
               vertices[i+13248] = new Point3D(xa,ya,z1);
            }else{
               vertices[i+13248] = new Point3D(xb,yb,z1);
            }
         }else if(i<8 && i>3){   //ala abajo a la izquierda
            if(i<6){
               vertices[i+13248] = new Point3D(xa,-ya,z1);
            }else{
               vertices[i+13248] = new Point3D(xb,-yb,z1);
            }
         }else if(i<12 && i>7){  //ala arriba a la derecha
            if(i<10){
               vertices[i+13248] = new Point3D(-xa,ya,z1);
            }else{
               vertices[i+13248] = new Point3D(-xb,yb,z1);
            }
         }else if(i<16 && i>11){ //ala abajo a la derecha
            if(i<14){
               vertices[i+13248] = new Point3D(-xa,-ya,z1);
            }else{
               vertices[i+13248] = new Point3D(-xb,-yb,z1);
            }
         }

      }
      //Pinta aristas de las 4 alas
      edges[14877] = new Edge(13248,13249);
      edges[14878] = new Edge(13248,13250);
      edges[14879] = new Edge(13251,13249);
      edges[14880] = new Edge(13251,13250);

      edges[14881] = new Edge(13252,13253);
      edges[14882] = new Edge(13252,13254);
      edges[14883] = new Edge(13255,13253);
      edges[14884] = new Edge(13255,13254);

      edges[14885] = new Edge(13256,13257);
      edges[14886] = new Edge(13256,13258);
      edges[14887] = new Edge(13259,13257);
      edges[14888] = new Edge(13259,13258);

      edges[14889] = new Edge(13260,13261);
      edges[14890] = new Edge(13260,13262);
      edges[14891] = new Edge(13263,13261);
      edges[14892] = new Edge(13263,13262);

      //pinta los cilindros de las orillas de las alas (armas)
      
      //arriba a la izquierda
      for(int j = 0; j<ch; j++){
         alfa = 280; //donde comienza a pintar el circulo
         if(j == 0){ //creo el primer circulo
            for(int i = 0; i<cv; i++){ //creo los vertices apartir del ultimo punto del arreglo utilizado
               vertices[i+1648] = new Point3D((float)((Math.cos(Math.toRadians(alfa)))*.2)-10,(float)((Math.sin(Math.toRadians(alfa)))*.2)+3,zcv+5);
               alfa += 360/cv;   // cambia el angulo para pintar el vertice
            }
            for(int i = 0; i<cv; i++){ // creo las aristas que unen vertice del circulo apartir del ultimo punto del arreglo utilizado 
               if(i<99){
                  edges[i+2477] = new Edge(i+1648,(i+1648)+1);
               }else if(i == 99){
                  edges[i+2477] = new Edge(i+1648,(i+1648)-99);
               }
            }   
         }
         alfa = 280; //donde comienza a pintar el circulo
         if(j == 1){ //pinto el otro ciruculo del cilindro de igual manera que el anterior 

            for(int i = 0; i<cv; i++){
               vertices[i+1748] = new Point3D((float)((Math.cos(Math.toRadians(alfa)))*.2)-10,(float)((Math.sin(Math.toRadians(alfa)))*.2)+3,zcv+1);
               alfa += 360/cv;
            }
            for(int i = 0; i<cv; i++){
               if(i<99){
                  edges[i+2577] = new Edge(i+1748,(i+1748)+1);
               }else if(i == 99){
                  edges[i+2577] = new Edge(i+1748,(i+1748)-99);
               }
               edges[i+2677] = new Edge( i+1648, i+1748 );   // pinto todas las aritas que unen a los dos circulos creados
            }   
         }
      }

      for(int j = 0; j<ch; j++){
         alfa = 280; //donde comienza a pintar el circulo
         if(j == 0){ //creo el primer circulo
            for(int i = 0; i<cv; i++){ //creo los vertices apartir del ultimo punto del arreglo utilizado
               vertices[i+2448] = new Point3D((float)((Math.cos(Math.toRadians(alfa)))*.05)-10,(float)((Math.sin(Math.toRadians(alfa)))*.05)+3,zcv+5);
               alfa += 360/cv;   // cambia el angulo para pintar el vertice
            }
            for(int i = 0; i<cv; i++){ // creo las aristas que unen vertice del circulo apartir del ultimo punto del arreglo utilizado 
               if(i<99){
                  edges[i+3677] = new Edge(i+2448,(i+2448)+1);
               }else if(i == 99){
                  edges[i+3677] = new Edge(i+2448,(i+2448)-99);
               }
            }   
         }
         alfa = 280; //donde comienza a pintar el circulo
         if(j == 1){ //pinto el otro ciruculo del cilindro de igual manera que el anterior 

            for(int i = 0; i<cv; i++){
               vertices[i+2548] = new Point3D((float)((Math.cos(Math.toRadians(alfa)))*.05)-10,(float)((Math.sin(Math.toRadians(alfa)))*.05)+3,zcv+10);
               alfa += 360/cv;
            }
            for(int i = 0; i<cv; i++){
               if(i<99){
                  edges[i+3777] = new Edge(i+2548,(i+2548)+1);
               }else if(i == 99){
                  edges[i+3777] = new Edge(i+2548,(i+2548)-99);
               }
               edges[i+3877] = new Edge( i+2548, i+2448 );   // pinto todas las aritas que unen a los dos circulos creados
            }   
         }
      }


      //arriba a la derecha
      for(int j = 0; j<ch; j++){
         alfa = 280; //donde comienza a pintar el circulo
         if(j == 0){ //creo el primer circulo
            for(int i = 0; i<cv; i++){ //creo los vertices apartir del ultimo punto del arreglo utilizado
               vertices[i+1848] = new Point3D((float)((Math.cos(Math.toRadians(alfa)))*.2)+10,(float)((Math.sin(Math.toRadians(alfa)))*.2)+3,zcv+5);
               alfa += 360/cv;   // cambia el angulo para pintar el vertice
            }
            for(int i = 0; i<cv; i++){ // creo las aristas que unen vertice del circulo apartir del ultimo punto del arreglo utilizado 
               if(i<99){
                  edges[i+2777] = new Edge(i+1848,(i+1848)+1);
               }else if(i == 99){
                  edges[i+2777] = new Edge(i+1848,(i+1848)-99);
               }
            }   
         }
         alfa = 280; //donde comienza a pintar el circulo
         if(j == 1){ //pinto el otro ciruculo del cilindro de igual manera que el anterior 

            for(int i = 0; i<cv; i++){
               vertices[i+1948] = new Point3D((float)((Math.cos(Math.toRadians(alfa)))*.2)+10,(float)((Math.sin(Math.toRadians(alfa)))*.2)+3,zcv+1);
               alfa += 360/cv;
            }
            for(int i = 0; i<cv; i++){
               if(i<99){
                  edges[i+2877] = new Edge(i+1948,(i+1948)+1);
               }else if(i == 99){
                  edges[i+2877] = new Edge(i+1948,(i+1948)-99);
               }
               edges[i+2977] = new Edge( i+1848, i+1948 );   // pinto todas las aritas que unen a los dos circulos creados
            }   
         }
      }

      for(int j = 0; j<ch; j++){
         alfa = 280; //donde comienza a pintar el circulo
         if(j == 0){ //creo el primer circulo
            for(int i = 0; i<cv; i++){ //creo los vertices apartir del ultimo punto del arreglo utilizado
               vertices[i+2648] = new Point3D((float)((Math.cos(Math.toRadians(alfa)))*.05)+10,(float)((Math.sin(Math.toRadians(alfa)))*.05)+3,zcv+1);
               alfa += 360/cv;   // cambia el angulo para pintar el vertice
            }
            for(int i = 0; i<cv; i++){ // creo las aristas que unen vertice del circulo apartir del ultimo punto del arreglo utilizado 
               if(i<99){
                  edges[i+3977] = new Edge(i+2648,(i+2648)+1);
               }else if(i == 99){
                  edges[i+3977] = new Edge(i+2648,(i+2648)-99);
               }
            }   
         }
         alfa = 280; //donde comienza a pintar el circulo
         if(j == 1){ //pinto el otro ciruculo del cilindro de igual manera que el anterior 

            for(int i = 0; i<cv; i++){
               vertices[i+2748] = new Point3D((float)((Math.cos(Math.toRadians(alfa)))*.05)+10,(float)((Math.sin(Math.toRadians(alfa)))*.05)+3,zcv+10);
               alfa += 360/cv;
            }
            for(int i = 0; i<cv; i++){
               if(i<99){
                  edges[i+4077] = new Edge(i+2748,(i+2748)+1);
               }else if(i == 99){
                  edges[i+4077] = new Edge(i+2748,(i+2748)-99);
               }
               edges[i+4177] = new Edge( i+2648, i+2748 );   // pinto todas las aritas que unen a los dos circulos creados
            }   
         }
      }

      //abajo a la izquierda
      for(int j = 0; j<ch; j++){
         alfa = 280; //donde comienza a pintar el circulo
         if(j == 0){ //creo el primer circulo
            for(int i = 0; i<cv; i++){ //creo los vertices apartir del ultimo punto del arreglo utilizado
               vertices[i+2048] = new Point3D((float)((Math.cos(Math.toRadians(alfa)))*.2)-10,(float)((Math.sin(Math.toRadians(alfa)))*.2)-3,zcv+5);
               alfa += 360/cv;   // cambia el angulo para pintar el vertice
            }
            for(int i = 0; i<cv; i++){ // creo las aristas que unen vertice del circulo apartir del ultimo punto del arreglo utilizado 
               if(i<99){
                  edges[i+3077] = new Edge(i+2048,(i+2048)+1);
               }else if(i == 99){
                  edges[i+3077] = new Edge(i+2048,(i+2048)-99);
               }
            }   
         }
         alfa = 280; //donde comienza a pintar el circulo
         if(j == 1){ //pinto el otro ciruculo del cilindro de igual manera que el anterior 

            for(int i = 0; i<cv; i++){
               vertices[i+2148] = new Point3D((float)((Math.cos(Math.toRadians(alfa)))*.2)-10,(float)((Math.sin(Math.toRadians(alfa)))*.2)-3,zcv+1);
               alfa += 360/cv;
            }
            for(int i = 0; i<cv; i++){
               if(i<99){
                  edges[i+3177] = new Edge(i+2148,(i+2148)+1);
               }else if(i == 99){
                  edges[i+3177] = new Edge(i+2148,(i+2148)-99);
               }
               edges[i+3277] = new Edge( i+2048, i+2148 );   // pinto todas las aritas que unen a los dos circulos creados
            }   
         }
      }

      for(int j = 0; j<ch; j++){
         alfa = 280; //donde comienza a pintar el circulo
         if(j == 0){ //creo el primer circulo
            for(int i = 0; i<cv; i++){ //creo los vertices apartir del ultimo punto del arreglo utilizado
               vertices[i+2848] = new Point3D((float)((Math.cos(Math.toRadians(alfa)))*.05)-10,(float)((Math.sin(Math.toRadians(alfa)))*.05)-3,zcv+1);
               alfa += 360/cv;   // cambia el angulo para pintar el vertice
            }
            for(int i = 0; i<cv; i++){ // creo las aristas que unen vertice del circulo apartir del ultimo punto del arreglo utilizado 
               if(i<99){
                  edges[i+4277] = new Edge(i+2848,(i+2848)+1);
               }else if(i == 99){
                  edges[i+4277] = new Edge(i+2848,(i+2848)-99);
               }
            }   
         }
         alfa = 280; //donde comienza a pintar el circulo
         if(j == 1){ //pinto el otro ciruculo del cilindro de igual manera que el anterior 

            for(int i = 0; i<cv; i++){
               vertices[i+2948] = new Point3D((float)((Math.cos(Math.toRadians(alfa)))*.05)-10,(float)((Math.sin(Math.toRadians(alfa)))*.05)-3,zcv+10);
               alfa += 360/cv;
            }
            for(int i = 0; i<cv; i++){
               if(i<99){
                  edges[i+4377] = new Edge(i+2948,(i+2948)+1);
               }else if(i == 99){
                  edges[i+4377] = new Edge(i+2948,(i+2948)-99);
               }
               edges[i+4477] = new Edge( i+2848, i+2948 );   // pinto todas las aritas que unen a los dos circulos creados
            }   
         }
      }
      //abajo a la derecha
      for(int j = 0; j<ch; j++){
         alfa = 280; //donde comienza a pintar el circulo
         if(j == 0){ //creo el primer circulo
            for(int i = 0; i<cv; i++){ //creo los vertices apartir del ultimo punto del arreglo utilizado
               vertices[i+2248] = new Point3D((float)((Math.cos(Math.toRadians(alfa)))*.2)+10,(float)((Math.sin(Math.toRadians(alfa)))*.2)-3,zcv+5);
               alfa += 360/cv;   // cambia el angulo para pintar el vertice
            }
            for(int i = 0; i<cv; i++){ // creo las aristas que unen vertice del circulo apartir del ultimo punto del arreglo utilizado 
               if(i<99){
                  edges[i+3377] = new Edge(i+2248,(i+2248)+1);
               }else if(i == 99){
                  edges[i+3377] = new Edge(i+2248,(i+2248)-99);
               }
            }   
         }
         alfa = 280; //donde comienza a pintar el circulo
         if(j == 1){ //pinto el otro ciruculo del cilindro de igual manera que el anterior 

            for(int i = 0; i<cv; i++){
               vertices[i+2348] = new Point3D((float)((Math.cos(Math.toRadians(alfa)))*.2)+10,(float)((Math.sin(Math.toRadians(alfa)))*.2)-3,zcv+1);
               alfa += 360/cv;
            }
            for(int i = 0; i<cv; i++){
               if(i<99){
                  edges[i+3477] = new Edge(i+2348,(i+2348)+1);
               }else if(i == 99){
                  edges[i+3477] = new Edge(i+2348,(i+2348)-99);
               }
               edges[i+3577] = new Edge( i+2248, i+2348 );   // pinto todas las aritas que unen a los dos circulos creados
            }   
         }
      }

      for(int j = 0; j<ch; j++){
         alfa = 280; //donde comienza a pintar el circulo
         if(j == 0){ //creo el primer circulo
            for(int i = 0; i<cv; i++){ //creo los vertices apartir del ultimo punto del arreglo utilizado
               vertices[i+3048] = new Point3D((float)((Math.cos(Math.toRadians(alfa)))*.05)+10,(float)((Math.sin(Math.toRadians(alfa)))*.05)-3,zcv+1);
               alfa += 360/cv;   // cambia el angulo para pintar el vertice
            }
            for(int i = 0; i<cv; i++){ // creo las aristas que unen vertice del circulo apartir del ultimo punto del arreglo utilizado 
               if(i<99){
                  edges[i+4577] = new Edge(i+3048,(i+3048)+1);
               }else if(i == 99){
                  edges[i+4577] = new Edge(i+3048,(i+3048)-99);
               }
            }   
         }
         alfa = 280; //donde comienza a pintar el circulo
         if(j == 1){ //pinto el otro ciruculo del cilindro de igual manera que el anterior 

            for(int i = 0; i<cv; i++){
               vertices[i+3148] = new Point3D((float)((Math.cos(Math.toRadians(alfa)))*.05)+10,(float)((Math.sin(Math.toRadians(alfa)))*.05)-3,zcv+10);
               alfa += 360/cv;
            }
            for(int i = 0; i<cv; i++){
               if(i<99){
                  edges[i+4677] = new Edge(i+3148,(i+3148)+1);
               }else if(i == 99){
                  edges[i+4677] = new Edge(i+3148,(i+3148)-99);
               }
               edges[i+4777] = new Edge( i+3048, i+3148 );   // pinto todas las aritas que unen a los dos circulos creados
            }   
         }
      }
      //************************Termina de pintar alas

      //************Crea media esfera***************
      float radio = .7f;
      alfa = 300;
      zcv = 2;
      for(int j = 0; j<60; j++){
         for(int i = 0; i<cv; i++){
         vertices[i+3248+(j*100)] = new Point3D((float)((Math.cos(Math.toRadians(alfa)))*radio),zcv,(float)((Math.sin(Math.toRadians(alfa)))*radio)+4);
               alfa += 360/cv;
         }
      for(int i = 0; i<cv; i++){
         if(i<99){
               edges[i+4877+(j*100)] = new Edge(i+3248+(j*100),(i+3248+(j*100))+1);
         }else if(i == 99){
               edges[i+4877+(j*100)] = new Edge(i+3248+(j*100),(i+3248+(j*100))-99);
         }
      }
      radio-=.01f;
      zcv +=.01;
      }
      //************TERMINA MEDIA ESFERA
      
      //************Crea circulos de las lanzas**************int numVertices = 9748;int numEdges = 11377;

      radio = .4f;
      alfa = 300;//arriba izquierda
      for(int j = 0; j<10; j++){
         for(int i = 0; i<cv; i++){
         vertices[i+9248+(j*100)] = new Point3D((float)((Math.cos(Math.toRadians(alfa)))*radio)-10,(float)((Math.sin(Math.toRadians(alfa)))*radio)+3,zcv+2.5f);
               alfa += 360/cv;
         }
      for(int i = 0; i<cv; i++){
         if(i<99){
               edges[i+10877+(j*100)] = new Edge(i+9248+(j*100),(i+9248+(j*100))+1);
         }else if(i == 99){
               edges[i+10877+(j*100)] = new Edge(i+9248+(j*100),(i+9248+(j*100))-99);
         }
      }
      zcv +=.02;
      }

      alfa = 300;//abajo izquierda
      for(int j = 0; j<10; j++){
         for(int i = 0; i<cv; i++){
         vertices[i+10248+(j*100)] = new Point3D((float)((Math.cos(Math.toRadians(alfa)))*radio)-10,(float)((Math.sin(Math.toRadians(alfa)))*radio)-3,zcv+2.5f);
               alfa += 360/cv;
         }
      for(int i = 0; i<cv; i++){
         if(i<99){
               edges[i+11877+(j*100)] = new Edge(i+10248+(j*100),(i+10248+(j*100))+1);
         }else if(i == 99){
               edges[i+11877+(j*100)] = new Edge(i+10248+(j*100),(i+10248+(j*100))-99);
         }
      }
      zcv +=.02;
      }
      
      alfa = 300;//arriba derecha
      for(int j = 0; j<10; j++){
         for(int i = 0; i<cv; i++){
         vertices[i+11248+(j*100)] = new Point3D((float)((Math.cos(Math.toRadians(alfa)))*radio)+10,(float)((Math.sin(Math.toRadians(alfa)))*radio)+3,zcv+2f);
               alfa += 360/cv;
         }
      for(int i = 0; i<cv; i++){
         if(i<99){
               edges[i+12877+(j*100)] = new Edge(i+11248+(j*100),(i+11248+(j*100))+1);
         }else if(i == 99){
               edges[i+12877+(j*100)] = new Edge(i+11248+(j*100),(i+11248+(j*100))-99);
         }
      }
      zcv +=.02;
      }

      alfa = 300;//abajo derecha
      for(int j = 0; j<10; j++){
         for(int i = 0; i<cv; i++){
         vertices[i+12248+(j*100)] = new Point3D((float)((Math.cos(Math.toRadians(alfa)))*radio)+10,(float)((Math.sin(Math.toRadians(alfa)))*radio)-3,zcv+2f);
               alfa += 360/cv;
         }
      for(int i = 0; i<cv; i++){
         if(i<99){
               edges[i+13877+(j*100)] = new Edge(i+12248+(j*100),(i+12248+(j*100))+1);
         }else if(i == 99){
               edges[i+13877+(j*100)] = new Edge(i+12248+(j*100),(i+12248+(j*100))-99);
         }
      }
      zcv +=.02;
      }

      

      canvas = new DisplayPanel();  // Create drawing surface and
      setContentPane(canvas);       //    install it as the applet's content pane.

      canvas.addFocusListener(this);   // Set up the applet to listen for events
      canvas.addKeyListener(this);     //                          from the canvas.
      canvas.addMouseListener(this);

   } // end init();



   class DisplayPanel extends JPanel {
      public void paintComponent(Graphics g) {
         super.paintComponent(g);

         if (focussed)
            g.setColor(Color.cyan);
         else
            g.setColor(Color.lightGray);

         int width = getSize().width;  // Width of the applet.
         int height = getSize().height; // Height of the applet.
         g.drawRect(0,0,width-1,height-1);
         g.drawRect(1,1,width-3,height-3);
         g.drawRect(2,2,width-5,height-5);

         if (!focussed) {
            g.setColor(Color.magenta);
            g.drawString("Click to activate",100,120);
            g.drawString("Use arrow keys to change azimuth and elevation",100,150);

         }
         else {

            double theta = Math.PI * azimuth / 180.0;
            double phi = Math.PI * elevation / 180.0;
            float cosT = (float)Math.cos( theta ), sinT = (float)Math.sin( theta );
            float cosP = (float)Math.cos( phi ), sinP = (float)Math.sin( phi );
            float cosTcosP = cosT*cosP, cosTsinP = cosT*sinP,
                  sinTcosP = sinT*cosP, sinTsinP = sinT*sinP;

            // project vertices onto the 2D viewport
            Point[] points;
            points = new Point[ vertices.length ];
            int j;
            int scaleFactor = width/8;
            float near = 2;  // distance from eye to near plane
            float nearToObj = 15f;  // distance from near plane to center of object
            for ( j = 0; j < vertices.length; ++j ) {
               float x0 = vertices[j].x;
               float y0 = vertices[j].y;
               float z0 = vertices[j].z;

               // compute an orthographic projection
               float x1 = cosT*x0 + sinT*z0;
               float y1 = -sinTsinP*x0 + cosP*y0 + cosTsinP*z0;

               // now adjust things to get a perspective projection
               float z1 = cosTcosP*z0 - sinTcosP*x0 - sinP*y0;
               x1 = x1*near/(z1+near+nearToObj);
               y1 = y1*near/(z1+near+nearToObj);

               // the 0.5 is to round off when converting to int
               points[j] = new Point(
                  (int)(width/2 + scaleFactor*x1 + 0.5),
                  (int)(height/2 - scaleFactor*y1 + 0.5)
               );
            }

            // draw the wireframe
            g.setColor( Color.black );
            g.fillRect( 0, 0, width, height );
            
            for ( j = 0; j < numEdges; ++j ) {
               
               g.setColor(new Color(255,255,255));
               g.drawLine(
                  points[ edges[j].a ].x, points[ edges[j].a ].y,
                  points[ edges[j].b ].x, points[ edges[j].b ].y
               );
            }
         }
      }  // end paintComponent()
    } // end nested class DisplayPanel

   // ------------------- Event handling methods ----------------------

   public void focusGained(FocusEvent evt) {
      focussed = true;
      canvas.repaint();
   }

   public void focusLost(FocusEvent evt) {
      focussed = false;
      canvas.repaint();
   }

   public void keyTyped(KeyEvent evt) {
   }  // end keyTyped()

   public void keyPressed(KeyEvent evt) {

      int key = evt.getKeyCode();  // keyboard code for the key that was pressed

      if (key == KeyEvent.VK_LEFT) {
         azimuth += 5;
         canvas.repaint();
      }
      else if (key == KeyEvent.VK_RIGHT) {
         azimuth -= 5;
         canvas.repaint();
      }
      else if (key == KeyEvent.VK_UP) {
         elevation -= 5;
         canvas.repaint();
      }
      else if (key == KeyEvent.VK_DOWN) {
         elevation += 5;
         canvas.repaint();
      }

   }  // end keyPressed()

   public void keyReleased(KeyEvent evt) {
      // empty method, required by the KeyListener Interface
   }

   public void mousePressed(MouseEvent evt) {
      canvas.requestFocus();
   }

   public void mouseEntered(MouseEvent evt) { }  // Required by the
   public void mouseExited(MouseEvent evt) { }   //    MouseListener
   public void mouseReleased(MouseEvent evt) { } //       interface.
   public void mouseClicked(MouseEvent evt) { }
   public void mouseDragged( MouseEvent e ) { }
} // end class
