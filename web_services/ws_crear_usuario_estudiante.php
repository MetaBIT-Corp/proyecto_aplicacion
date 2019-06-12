<?php 

if (isset($_REQUEST['idusuario'])&&isset($_REQUEST['nomusuario'])&&isset($_REQUEST['clave'])&&isset($_REQUEST['rol'])) {
	  
	$idusuario = $_REQUEST['idusuario']; 
	$nomusuario = $_REQUEST['nomusuario'];
	$clave = $_REQUEST['clave'];
	$rol = $_REQUEST['rol'];

	$servidor = 'localhost';
	$usuario = 'eisi_q';
	$database = 'EISI_Q';
	$password = 'enQest*s%8102%';

	$respuesta = array('resultado' =>  0);
	json_encode($respuesta);

	
	$conexion = mysqli_connect($servidor, $usuario, $password, $database);

	if($conexion){
		$query = "INSERT INTO USUARIO VALUES ('$idusuario','$nomusuario','$clave','$rol');";
		$resultado = mysqli_query($conexion,$query);

		if($resultado){
			$respuesta = array('resultado' =>  1);
		}
	}
	
	echo json_encode($respuesta);
	mysqli_close($conexion);

}

 ?>