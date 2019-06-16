<?PHP

$json=array();

if(isset($_GET["id_est"]) && isset($_GET["id_clave"]) && isset($_GET["fecha_inicio_intento"]) && isset($_GET["numero_intento"])){

	$id_est=$_GET['id_est'];
	$id_clave=$_GET['id_clave'];
	$fecha_inicio_intento=$_GET['fecha_inicio_intento'];
	$numero_intento = $_GET['numero_intento'];
	$id_encuesta = $_GET['id_encuesta'];

	$conexion = include '../conexion_bd.php';

		/* comprobar la conexion */
	if (mysqli_connect_errno()) {
	    printf("Conexion fallida: %s\n", mysqli_connect_error());
	    exit();
	}

	/* comprobar si el servidor sigue funcionando */
	if (mysqli_ping($conexion)) {
	    $insert="INSERT INTO INTENTO(id_est, id_clave, fecha_inicio_intento, numero_intento, id) VALUES ({$id_est},{$id_clave},'{$fecha_inicio_intento}',{$numero_intento},{$id_encuesta})";
		
		$resultado_insert=mysqli_query($conexion,$insert);
		
		if($resultado_insert){
			$resulta["Resultado"]='1';
			$json['intento_create'][]=$resulta;
			echo json_encode($json);
		}
		else{
			$resulta["Resultado"]='0';
			$json['intento_create'][]=$resulta;
			echo json_encode($json);
		}
		mysqli_close($conexion);
	} else {
	    printf ("Error: %s\n", mysqli_error($conexion));
	}
	
}
else{
		$resulta["Error"]="WS NO RETORNA";
		$json['intento'][]=$resulta;
		echo json_encode($json);
	}
?>