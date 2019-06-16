<?php 

if (isset($_REQUEST['id_encuesta'])) {
	
	$conexion = include '../conexion_bd.php';

	$id_encuesta = $_REQUEST['id_encuesta'];

	$respuesta = array('resultado' =>  0);
	json_encode($respuesta);

	if($conexion){
		$query = "DELETE FROM ENCUESTA WHERE ID_ENCUESTA = '$id_encuesta';";
		$resultado = mysqli_query($conexion,$query);

		if($resultado){
			$respuesta = array('resultado' =>  1);
		}

	}
	
	echo json_encode($respuesta);
	mysqli_close($conexion);

}

 ?>
