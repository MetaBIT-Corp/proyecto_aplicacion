<?php

    $conexion = include '../conexion_bd.php';

    if($conexion){

        $query = "SELECT * FROM ESTUDIANTE;";

        $resultado = mysqli_query($conexion,$query);

        while($fila = mysqli_fetch_array($resultado)){
            $respuesta['ESTUDIANTE'][] = $fila;
        }
    }

    mysqli_close($conexion);
    echo json_encode($respuesta);

?>