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
		
		$query_insert = "INSERT INTO  ESTUDIANTE (CARNET,NOMBRE,ACTIVO,ANIO_INGRESO) VALUES ('$carnet','$nombre',$activo,'$anio_ingreso');";
		$resultado_insert = mysqli_query($conexion,$query_insert);

		if($resultado_insert){
			$respuesta = array('resultado' =>  1);
		}

	}
	
	echo json_encode($respuesta);
	mysqli_close($conexion);

}

 ?>