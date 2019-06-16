<?PHP

$json=array();

if(isset($_GET["id_opcion"]) && isset($_GET["id_pregunta"]) && isset($_GET["texto_respuesta"])){

	$id_opcion=$_GET['id_opcion'];
	$id_pregunta=$_GET['id_pregunta'];
	$texto_respuesta = $_GET['texto_respuesta'];
	
	$conexion = include '../conexion_bd.php';

	$select = "SELECT id_intento FROM INTENTO ORDER BY ID_INTENTO DESC LIMIT 1";


	/* comprobar si el servidor sigue funcionando */
	if (mysqli_ping($conexion)) {
			$resultado_select=mysqli_query($conexion,$select);
			$id_intento = mysqli_fetch_array($resultado_select)[0];
			
			$insert="INSERT INTO RESPUESTA(id_opcion, id_intento, id_pregunta, texto_respuesta) VALUES ({$id_opcion},{$id_intento},{$id_pregunta},'{$texto_respuesta}')";
			
			$resultado_insert=mysqli_query($conexion,$insert);
			
			if($resultado_insert){
				$resulta["Resultado"]='1';
				$json['respuesta_create'][]=$resulta;
				echo json_encode($json);
			}
			else{
				$resulta["Resultado"]='0';
				$json['respuesta_create'][]=$resulta;
				echo json_encode($json);
			}
			mysqli_close($conexion);

		}
		else {
		    printf ("Error: %s\n", mysqli_error($conexion));
		}
	
}
else{
		$resulta["Error"]="WS NO RETORNA";
		$json['respuesta'][]=$resulta;
		echo json_encode($json);
	}
?>