<?php 

	if (isset($_REQUEST['nomtabla'])&&isset($_REQUEST['nomcampoid'])) {
		
		$conexion = include '../conexion_bd.php';

		$nomtabla = $_REQUEST['nomtabla'];
		$nomcampoid = $_REQUEST['nomcampoid'];
		
		$respuesta = array('resultado' =>  0);
		
		json_encode($respuesta);


		if($conexion){
			$query = "SELECT MAX($nomcampoid) FROM $nomtabla";
			$resultado = mysqli_query($conexion,$query);

			if($resultado){
				$respuesta = array('resultado' =>  $resultado);
			}
		}
		
		echo json_encode($respuesta);
		mysqli_close($conexion);

	}

?>