<?PHP

$json=array();

if(isset($_GET["fecha_final_intento"]) && isset($_GET["nota_intento"])){

	$fecha_final_intento = $_GET['fecha_final_intento'];
	$nota_intento = $_GET['nota_intento'];
	$nota_intento = (double)$nota_intento;
	
	$conexion = include '../conexion_bd.php';

	$select = "SELECT id_intento FROM INTENTO ORDER BY ID_INTENTO DESC LIMIT 1";


		/* comprobar la conexión */
	if (mysqli_connect_errno()) {
	    printf("Conexión fallida: %s\n", mysqli_connect_error());
	    exit();
	}

	/* comprobar si el servidor sigue funcionando */
	if (mysqli_ping($conexion)) {
		$resultado_select=mysqli_query($conexion,$select);
		$id_intento = mysqli_fetch_array($resultado_select)[0];
		
		$update_intento = "UPDATE INTENTO SET fecha_final_intento='{$fecha_final_intento}', nota_intento={$nota_intento} WHERE id_intento={$id_intento}";
	
		$resultado_update=mysqli_query($conexion,$update_intento);
		
		if($resultado_update){
			$resulta["Resultado"]='1';
			$json['intento_update'][]=$resulta;
			echo json_encode($json);
		}
		else{
			$resulta["Resultado"]='0';
			$json['intento_update'][]=$resulta;
			echo json_encode($json);
		}
		mysqli_close($conexion);
		   
		} else {
		    printf ("Error: %s\n", mysqli_error($conexion));
		}
	
}
else{
		$resulta["Error"]="WS NO RETORNA";
		$json['respuesta'][]=$resulta;
		echo json_encode($json);
	}
?>