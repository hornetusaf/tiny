0:       LD       6,0(0)      --cargar la maxima direccion desde la localidad 0
1:       ST       0,0(0)      --limpio el registro de la localidad 0
2:       LDC       0,0(0)      --cargar 0 en registro AC
3:       ST       0,0(0)      --cargar 0 en DMEM 0
4:       LDC       1,0(0)      --cargar 0 en registro AC1
5:       LD       0,0(1)      --cargar la maxima direccion desde la localidad 0
7:       LDC       0,10(0)      --Cargar linea
8:       ST       0,1(1)      --subiendo posicion de memoria
6:       JNE       0,4(7)      --voy 4 instrucciones mas alla if verdadero (AC==0)
*      -> Declaracion de Variable
10:       LD       0,4(5)      --cargar valor de Variable: f.b
*      <-Fin Declaracion de Variable
*      -> asignacion
*      -> constante
11:       LDC       0,5(0)      --cargar constante: 5
*      <- constante
12:       ST       0,5(5)      --asignacion: almaceno el valor para el id b
*      <- asignacion
*      -> escribir
*      ->Variable
13:       LD       0,3(5)      --cargar valor de Variable: f.a
*      <- Variable
14:       OUT       0,0,0      --escribir: genero la salida de la expresion
*      <- escribir
*      -> escribir
*      ->Variable
15:       LD       0,2(5)      --cargar valor de Variable: f.c
*      <- Variable
16:       OUT       0,0,0      --escribir: genero la salida de la expresion
*      <- escribir
*      -> escribir
*      ->Variable
17:       LD       0,5(5)      --cargar valor de Variable: f.b
*      <- Variable
18:       OUT       0,0,0      --escribir: genero la salida de la expresion
*      <- escribir
*      -> asignacion
19:       LDC       1,0(0)      --cargar Valor 0
20:       LD       0,2(1)      --Descargando Valor de: f.c
21:       ST       0,10(1)      --subiendo posicion de memoria identificador
22:       LDC       4,0(0)      --cargar constante: 0
23:       LDA       3,1(7)      --cargar Direccion de retorno
24:       LD       7,9(4)      --Salto a la funcion
25:       ST       0,3(5)      --asignacion: almaceno el valor para el id a
*      <- asignacion
26:       LDC       1,0(0)      --cargar Valor 0
27:       LD       0,3(1)      --Descargando Valor de: f.a
28:       LDA       7,0(3)      --Devolver Direccion de retorno
9:       LDC       7,30(0)      --Cargar linea
29:       LDA       7,0(3)      --Devolver Direccion de retorno
30:       LDC       1,0(0)      --cargar 0 en registro AC1
31:       LD       0,0(1)      --cargar la maxima direccion desde la localidad 0
33:       LDC       0,36(0)      --Cargar linea
34:       ST       0,9(1)      --subiendo posicion de memoria
32:       JNE       0,4(7)      --voy 4 instrucciones mas alla if verdadero (AC==0)
*      -> asignacion
*      -> constante
36:       LDC       0,6(0)      --cargar constante: 6
*      <- constante
37:       ST       0,10(5)      --asignacion: almaceno el valor para el id d
*      <- asignacion
38:       LDC       1,0(0)      --cargar Valor 0
39:       LD       0,10(1)      --Descargando Valor de: f2.d
40:       LDA       7,0(3)      --Devolver Direccion de retorno
35:       LDC       7,42(0)      --Cargar linea
41:       LDA       7,0(3)      --Devolver Direccion de retorno
*      -> Declaracion de Variable
42:       LD       0,11(5)      --cargar valor de Variable: @.x
*      <-Fin Declaracion de Variable
*      -> Declaracion de Variable
43:       LD       0,12(5)      --cargar valor de Variable: @.y
*      <-Fin Declaracion de Variable
*      -> Declaracion de Variable
44:       LD       0,13(5)      --cargar valor de Variable: @.z
*      <-Fin Declaracion de Variable
*      -> asignacion
*      -> constante
45:       LDC       0,1(0)      --cargar constante: 1
*      <- constante
46:       ST       0,11(5)      --asignacion: almaceno el valor para el id x
*      <- asignacion
*      -> asignacion
*      -> constante
47:       LDC       0,2(0)      --cargar constante: 2
*      <- constante
48:       ST       0,12(5)      --asignacion: almaceno el valor para el id y
*      <- asignacion
*      -> asignacion
49:       LDC       1,0(0)      --cargar Valor 0
50:       LD       0,12(1)      --Descargando Valor de: @.y
51:       ST       0,2(1)      --subiendo posicion de memoria identificador
52:       LDC       1,0(0)      --cargar Valor 0
53:       LD       0,11(1)      --Descargando Valor de: @.x
54:       ST       0,3(1)      --subiendo posicion de memoria identificador
55:       LDC       4,0(0)      --cargar constante: 0
56:       LDA       3,1(7)      --cargar Direccion de retorno
57:       LD       7,1(4)      --Salto a la funcion
58:       ST       0,13(5)      --asignacion: almaceno el valor para el id z
*      <- asignacion
*      -> escribir
*      ->Variable
59:       LD       0,13(5)      --cargar valor de Variable: @.z
*      <- Variable
60:       OUT       0,0,0      --escribir: genero la salida de la expresion
*      <- escribir
*      Fin de la ejecucion.
61:       HALT       0,0,0