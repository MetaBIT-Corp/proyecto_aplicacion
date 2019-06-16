<?php 

if (isset($_REQUEST['carnet'])&&isset($_REQUEST['nombre'])&&isset($_REQUEST['activo'])&&isset($_REQUEST['anio_ingreso'])) {
	
	$conexion = include '../conexion_bd.php';
	
	$carnet = $_REQUEST['carnet'];
	$nombre = $_REQUEST['nombre'];
	$activo = $_REQUEST['activo'];
	$anio_ingreso = $_REQUEST['anio_ingreso'];

	$respuesta = array('resultado' =>  0);
	json_encode($respuesta);


	if($conexion){
		$query = "INSERT INTO ESTUDIANTE VALUES ('$carnet','$nombre','$activo','$anio_ingreso');";
		$resultado = mysqli_query($conexion,$query);

		if($resultado){
			$respuesta = array('resultado' =>  1);
		}
	}
	
	echo json_encode($respuesta);
	mysqli_close($conexion);

}

 ?>
