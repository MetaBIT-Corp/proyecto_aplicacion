<?php

	$conexion = include '../conexion_bd.php';

	if($conexion){
		
		if (isset($_REQUEST['parametro'])) {

			$parametro = $_REQUEST['parametro'];

			$query = "SELECT * FROM ESTUDIANTE WHERE CARNET LIKE '%".$parametro."%';";

			$resultado = mysqli_query($conexion,$query);

			while($fila = mysqli_fetch_array($resultado)){
	    		$respuesta['ESTUDIANTE'][] = $fila;
	    	}

		}
	}

	mysqli_close($conexion);
	echo json_encode($respuesta);

?>