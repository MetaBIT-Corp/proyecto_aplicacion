<?php

if (isset($_REQUEST['id'])) {

    $conexion  = include '../conexion_bd.php';
    $id        = $_REQUEST['id'];
    $respuesta = array('resultadoEstudiante' => 0, 'resultadoUsuario' => 0);

    if ($conexion) {
        $query     = "DELETE FROM ESTUDIANTE WHERE ID_EST = '$id';";
        $resultado = mysqli_query($conexion, $query);
        if ($resultado) {
            $respuesta['resultadoEstudiante'] = 1;
        }

        $query1     = "DELETE FROM USUARIO WHERE IDUSUARIO = '$id';";
        $resultado1 = mysqli_query($conexion, $query1);
        if ($resultado1) {
            $respuesta['resultadoUsuario'] = 1;
        }

    }

    echo json_encode($respuesta);
    mysqli_close($conexion);

} else {
    echo "Funcionando Correctamente";
}
?>