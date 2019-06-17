<?PHP

$json=array();  // se encarga de almacenar la información que retorna el webService
	
    //validación
    if(isset($_GET["id_est"]) &&  
       isset($_GET["carnet"]) && 
       isset($_GET["nombre"]) && 
       isset($_GET["activo"]) && 
       isset($_GET["anio_ingreso"])){
        
		$id_est = $_GET["id_est"];
        $carnet = $_GET["carnet"];
        $nombre = $_GET["nombre"];
        $activo = $_GET["activo"];
        $anio_ingreso = $_GET["anio_ingreso"];
		
        //nos conectamos a la base
		$conexion = include '../conexion_bd.php';
        
		$insert= "UPDATE ESTUDIANTE SET 
        ID_EST='{$id_est}',
        CARNET='{$carnet}',
        NOMBRE='{$nombre}',
        ACTIVO='{$activo}',
        ANIO_INGRESO='{$anio_ingreso}'
        WHERE ID_EST='{$id_est}'";
        
		$resultado_insert=mysqli_query($conexion,$insert);
		
		if($resultado_insert){
			$json['resultado']=1;
			mysqli_close($conexion);
			echo json_encode($json);
		}
		else{
			$json['resultado']=0;
			echo json_encode($json);
		}
		
	}
	else{
			$json['resultado']=0;
			echo json_encode($json);
		}


?>