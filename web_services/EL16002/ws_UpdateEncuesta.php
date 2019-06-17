<?php

$json=json_decode(file_get_contents('php://input'),true);
if (!empty($json)) {
    $conexion = include '../conexion_bd.php';

    $id          = $json['ID_ENCUESTA'];
    $docente     = $json['ID_PDG_DCN'];
    $titulo      = $json['TITULO_ENCUESTA'];
    $descripcion = $json['DESCRIPCION_ENCUESTA'];
    $fechain     = $json['FECHA_INICIO_ENCUESTA'];
    $fechafin    = $json['FECHA_FINAL_ENCUESTA'];

    $respuesta = array('resultado' =>  0);

    if ($conexion) {
        $query     = "UPDATE ENCUESTA SET ID_PDG_DCN='$docente',TITULO_ENCUESTA='$titulo', DESCRIPCION_ENCUESTA= '$descripcion',FECHA_INICIO_ENCUESTA='$fechain',FECHA_FINAL_ENCUESTA='$fechafin'WHERE ID_ENCUESTA='$id';";
        $resultado = mysqli_query($conexion, $query);
        if ($resultado) {
            $respuesta['resultado'] = 1;
        }

    }

    echo json_encode($respuesta);
    mysqli_close($conexion);

} else {
    echo "Funciona correctamente";
}

?>