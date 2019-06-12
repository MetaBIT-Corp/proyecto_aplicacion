<?php 

if (isset($_REQUEST['id_encuesta'])) {
	
	$id_encuesta = $_REQUEST['id_encuesta'];

	$servidor = 'localhost';
	$usuario = 'eisi_q';
	$database = 'EISI_Q';
	$password = 'enQest*s%8102%';

	$respuesta = array('resultado' =>  0);
	json_encode($respuesta);

	
	$conexion = mysqli_connect($servidor, $usuario, $password, $database);

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