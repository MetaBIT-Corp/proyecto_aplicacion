<?php 

if (isset($_REQUEST['id_est'])&&isset($_REQUEST['idusuario'])&&isset($_REQUEST['carnet'])&&isset($_REQUEST['nombre'])&&isset($_REQUEST['activo'])&&isset($_REQUEST['anio_ingreso'])) {
	
	$id_est = $_REQUEST['id_est'];  
	$idusuario = $_REQUEST['idusuario'];
	$carnet = $_REQUEST['carnet'];
	$nombre = $_REQUEST['nombre'];
	$activo = $_REQUEST['activo'];
	$anio_ingreso = $_REQUEST['anio_ingreso'];

	$servidor = 'localhost';
	$usuario = 'eisi_q';
	$database = 'EISI_Q';
	$password = 'enQest*s%8102%';

	$respuesta = array('resultado' =>  0);
	json_encode($respuesta);

	
	$conexion = mysqli_connect($servidor, $usuario, $password, $database);

	if($conexion){
		$query = "INSERT INTO ESTUDIANTE VALUES ('$id_est','$idusuario','$carnet','$nombre','$activo','$anio_ingreso');";
		$resultado = mysqli_query($conexion,$query);

		if($resultado){
			$respuesta = array('resultado' =>  1);
		}
	}
	
	echo json_encode($respuesta);
	mysqli_close($conexion);

}

 ?>