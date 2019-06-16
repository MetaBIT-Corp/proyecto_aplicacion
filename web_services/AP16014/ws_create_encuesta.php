<?PHP

$json=array();  // se encarga de almacenar la información que retorna el webService
	
    //validación
    if(isset($_GET["id_encuesta"]) &&
       isset($_GET["descripcion_encuesta"]) && 
       isset($_GET["fecha_inicio_encuesta"]) && 
       isset($_GET["fecha_final_encuesta"]) && 
       isset($_GET["id_pdg_dcn"]) &&
       isset($_GET["titulo_encuesta"])
      ){
        
        $id_encuesta= $_GET['id_encuesta'];
		$id_pdg_dcn=$_GET['id_pdg_dcn'];
		$descripcion_encuesta=$_GET['descripcion_encuesta'];
		$fecha_inicio_encuesta=$_GET['fecha_inicio_encuesta'];
        $fecha_final_encuesta=$_GET['fecha_final_encuesta'];
        $titulo_encuesta=$_GET['titulo_encuesta'];
		
        //nos conectamos a la base
		$conexion = include '../conexion_bd.php';
        
        if($conexion){
        
		$insert= "INSERT INTO ENCUESTA(ID_ENCUESTA, ID_PDG_DCN, TITULO_ENCUESTA, DESCRIPCION_ENCUESTA,FECHA_INICIO_ENCUESTA, FECHA_FINAL_ENCUESTA) 
        VALUES (
        '{$id_encuesta}',
        '{$id_pdg_dcn}',
        '{$titulo_encuesta}' , 
        '{$descripcion_encuesta}',
        '{$fecha_inicio_encuesta}',
        '{$fecha_inicio_encuesta}'
        )";
        
		$resultado_insert=mysqli_query($conexion,$insert);
		
		if($resultado_insert){
			$json['resultado']=1;
			mysqli_close($conexion);
			echo json_encode($json);
		}
		else{
			$resulta["resultado"]=0;
			$json['alumno'][]=$resulta;
			echo json_encode($json);
		}
		}else{
            $json['resultado']=0;
			echo json_encode($json);
        }
	}
	else{
			$json['resultado'][]=0;
			echo json_encode($json);
		}

?>