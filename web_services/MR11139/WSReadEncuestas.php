<?php

	$conexion = include '../conexion_bd.php';

	if($conexion){

	    if (isset($_REQUEST['idusuario'])) {

	        $idUsuario = $_REQUEST['idusuario'];

	    	$query = "SELECT ENCUESTA.ID_ENCUESTA,ENCUESTA.ID_PDG_DCN,ENCUESTA.TITULO_ENCUESTA,ENCUESTA.DESCRIPCION_ENCUESTA,ENCUESTA.FECHA_INICIO_ENCUESTA,ENCUESTA.FECHA_FINAL_ENCUESTA FROM ENCUESTA INNER JOIN PDG_DCN_DOCENTE ON ENCUESTA.ID_PDG_DCN=PDG_DCN_DOCENTE.ID_PDG_DCN WHERE PDG_DCN_DOCENTE.IDUSUARIO=$idUsuario;";
	    	
	    	$resultado = mysqli_query($conexion,$query);

	    	while($fila = mysqli_fetch_array($resultado)){
	    		$respuesta['ENCUESTA'][] = $fila;
	    	}
	    }

	}

	mysqli_close($conexion);
	echo json_encode($respuesta);

?>