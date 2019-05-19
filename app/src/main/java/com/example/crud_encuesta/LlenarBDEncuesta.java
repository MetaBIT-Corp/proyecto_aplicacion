package com.example.crud_encuesta;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class LlenarBDEncuesta {

    public LlenarBDEncuesta(){
    }

    //Tabla Tipo_item
    final int[] id_tipo_item_pk = {1, 2, 3, 4};
    final String[] nombre_tipo_item = {"Opcion Multiple", "Verdadero/Falso", "Emparejamiento", "Respuesta Corta"};

    //Tabla Grupo_carga
    final int[] id_grup_car_pk = {1, 2, 3};
    final String[] tipo = {"Teorico", "Laboratorio", "Discusion"};

    //Tabla Facultad
    final int[] id_facultad_pk = {1, 2, 3, 4, 5};
    final String[] nombre_facultad = {"Ingenieria y Arquitectura", "Jurisprudencia", "Medicina", "Economia", "Agronomia"};

    //Tabla Escuela
    final int[] id_escuela_pk = {1, 2, 3, 4, 5, 6, 7};
    final int[] id_facultad = {1, 1, 1, 1, 1, 1, 1};
    final String[] nombre_escuela = {"Ingenier a de Sistemas Informaticos", "Ingenieria Mecanica", "Ingenieria Electrica", "Ingenieria Civil", "Ingeniera Industrial", "Ingenieria Quimica y de Alimentos", "Arquitectura"};
    final String[] codigo_escuela = {"I0001", "I0002", "I0003", "I0004", "I0005", "I0006", "I0007"};

    //Tabla Carrera
    final int[] id_carrera_pk = {1, 2, 3, 4, 5, 6, 7, 8};
    final int[] id_escuela = {1, 2, 3, 4, 5, 6, 6, 7};
    final String[] nombre_carrera = {"Ingenier a de Sistemas Informaticos", "Ingenieria Mecanica", "Ingenieria Electrica", "Ingenieria Civil", "Ingeniera Industrial", "Ingenieria Quimica","Ingeniria de Alimentos", "Arquitectura"};

    //Tabla Pensum
    final int[] id_pensum_pk = {1, 2, 3, 4, 5, 6, 7, 8};
    final int[] id_carrera = {1, 2, 3, 4, 5, 6, 7, 8};
    final String[] anio_pensum = {"1998", "1998", "1998", "1998", "1998", "1998", "1998", "1998"};

    //Tabla Materia
    final int[] id_materia_pk = {1, 2, 3, 4, 5};
    final String[] codigo_mat = {"HDP115", "ARC115", "PDM115", "SGG115", "IGF115"};
    final String[] nombre_mat = {
            "Herramientas de Productividad",
            "Arquitectura de Computadoras",
            "Programacion para Dispositivos Moviles",
            "Sistemas de Informacion Geografica",
            "Ingenieria de Software"
    };
    final int[] es_electiva = {0, 0, 1, 1, 1};
    final int[] cantidad_maxima_preguntas = {50, 50, 50, 50, 50};

    //Tabla Usuario
    final int[] id_usuario_pk = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    final String[] nomusuario = {"joel", "javier", "ernesto", "camren", "carolina", "jose", "marco", "mauricio", "evelyn", "lisset"};
    final String[] clave = {"12345", "12345", "12345", "12345", "12345", "12345", "12345", "12345", "12345", "12345" };
    final int[] rol = {0, 1, 1, 1, 1, 2, 2, 2, 2, 2};

    //Tabla Docente
    final int[] id_docente_pk = {1, 2, 3, 4, 5};
    final int[] id_escuela_docente = {1, 1, 1, 1, 1};
    final String[] carnet_dcn = {"GY87123", "IJ99299", "LL85399", "GU98234", "PL89002"};
    final String[] anio_titulacion = {"2000", "2001", "2005", "2007", "2003"};
    final int[] activo = {1, 1, 1, 1, 1};
    final int[] tipojornada = {1, 1, 1, 1, 1};
    final String[] descripciondocente = {"Analista de sistemas", "Ingeniero de Software", "Maestria en seguridad informatica", "Programador web", "Maestria en ciencias de la computacion"};
    final int[] id_cargo_actual = {1, 1, 1, 1, 1};
    final int[] id_segundo_cargo = {1, 1, 1, 1, 1};
    final String[] nombre_docente = {"Joel Palacios", "Javier Galdamez", "Ernesto Hernandez", "Carmen Mendoza", "Carolina Palencia"};
    final int[] idusuario = {1, 2, 3, 4, 5};

    //tabla Estudiante
    final int[] id_estudiante_pk = {1, 2, 3, 4, 5};
    final String[] carnet = {"FP16007", "CE15023", "VO11101", "GG162000", "AD17002"};
    final String[] nombre = {"Jose Flores", "Marco Chicas", "Mauricio Villeda", "Evelyn Galdamez", "Lisset Avrego"};
    final String[] anio_ingreso = {"2016", "2015", "2011", "2016", "2017"};
    final int[] activo_estudiante = {1, 1, 1, 1, 1};
    final int[] idusuario_estudiante = {6, 7, 8, 9, 10};

    //Tabla Materia_ciclo ----------------------->Dudas al llenar esta tabla
    final int[] id_materia_ciclo_pk = {1, 2, 3, 4, 5};
    final int[] id_cat_mat = {1, 2, 3, 4, 5};
    final int[] ciclo = {1, 2, 3, 4, 5};
    final String[] anio = {"2019", "2019", "2019", "2019", "2019"};

    //Tabla Encuesta
    final int[] id_encuesta_pk = {1, 2, 3, 4, 5};
    final int[] id_pdg_dcn_encuesta = {1, 1, 1, 2, 2};
    final String[] titulo_encuesta = {"Opinion", "Horario", "Fecha parcial", "Clases", "Teorico"};
    final String[] descripcion_encuesta = {
            "Opinion sobre los docentes de la FIA",
            "Horario sugerido para practicas libre",
            "Fecha sugerida para el proximo parcial",
            "Cambio de horario de clases",
            "Pasar grupo teorico para el dia sabado"
    };
    final String[] fecha_inicio_encuesta = {"2019-05-20 12:00:00", "2019-05-21 12:00:00", "2019-05-22 12:00:00", "2019-05-23 12:00:00", "2019-05-24 12:00:00"};
    final String[] fecha_final_encuesta = {"2019-05-20 14:00:00", "2019-05-21 14:00:00", "2019-05-22 14:00:00", "2019-05-23 14:00:00", "2019-05-24 14:00:00"};

    //Tabla Evaluacion
    final int[] id_evaluacion_pk = {1, 2, 3, 4, 5};
    final int[] id_carg_aca = {1, 2, 3, 4, 5};
    final int[] duracion = {60, 50, 50, 100, 120};
    final int[] intento = {1, 2, 1, 1, 1};
    final String[] nombre_evaluacion = {"Parcial 1", "Parcial 2", "Corto 1", "Corto 2", "Control de lectura"};
    final String[] descripcion_evaluacion = {"Unidades I, II y III", "Unidades IV, V y VI", "Material extra", "Presentaciones", "Material visto en clases"};

    //Tabla Turno
    final int[] id_turno_pk = {1, 2, 3, 4, 5};
    final int[] id_evaluacion = {1, 1, 3, 4, 5};
    final String[] fecha_inicio_turno = {"2019-05-20 12:00:00", "2019-05-21 12:00:00", "2019-05-22 12:00:00", "2019-05-23 12:00:00", "2019-05-24 12:00:00"};
    final String[] fecha_final_turno = {"2019-05-20 14:00:00", "2019-05-21 14:00:00", "2019-05-22 14:00:00", "2019-05-23 14:00:00", "2019-05-24 14:00:00"};
    final int[] visibilidad = {1, 1, 0, 1, 1};
    final String[] contrasenia = {"12345", "12345", "12345", "12345", "12345"};

    //Clave
    final int[] id_clave_pk = {1, 2, 3, 4, 5};
    final int[] id_turno = {1, 2, 3, 0, 0};
    final int[] id_encuesta = {0, 0, 0, 1, 2};
    final int[] numero_clave = {1, 2, 3, 4, 5};

    //Tabla Area
    final int[] id_area_pk = {1, 2, 3, 4};
    final int[] id_cat_mat_area = {1, 1, 1, 1};
    final int[] id_pdg_dcn_area = {2, 2, 2, 2};
    final int[] id_tipo_item = {1, 2, 3, 4};
    final String[] titulo = {"Informacion geografica", "Administracion", "Padre de la Administracion", "Android"};

    //Tabla Emparejamiento
    final int[] id_emparejamiento_pk = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
    final int[] id_area = {1, 1, 1, 1, 1, 1, 3, 4, 4, 4, 2, 2, 2};
    final String[] descripcion_grupo_emp = {"", "", "", "", "", "", "Empareje correctamente las siguientes preguntas", "", "", "", "", "", ""};

    //Tabla Pregunta
    final int[] id_pregunta_pk = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    final int[] id_grupo_emp = {1, 2, 3, 4, 5, 6, 7, 7, 7, 8, 9, 10, 11, 12, 13};
    final String[] pregunta = {
            "¿Ubicacion de la UES?",
            "¿Ciencia que estudia los hongos?",
            "¿Proyecciones principales?",
            "¿Padre de la geologia?",
            "¿Padre de los GIS?",
            "¿Cual es la proyeccion que se utiliza en El Salvador?",
            "¿Padre de la administracion cientifica?",
            "¿Padre de la administracion moderna?",
            "¿Padre de los recursos humanos?",
            "¿Actualmente a cual empresa pertenece Android?",
            "¿Actualmente a cual empresa pertenece IOS?",
            "¿Actualmente a cual empresa pertenece Windows?",
            "Historia de usuario se refiere a la descripcion de una funcionalidad que debe incorporar un sistema de software",
            "En una organizacion inteligente, el aprendizaje en equipo es la disciplina que constituye el elemento principal de interconexion con las otras",
            "La administracion es escencial en todas corporaciones organizadas, asi como en todos los niveles de la organizacion"
    };

    //Tabla Opcion
    final int[] id_opcion_pk = {
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
            11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
            21, 22, 23, 24, 25, 26, 27, 28, 29, 30,
            31, 32, 33, 34, 35, 36, 37, 38, 39, 40
    };
    final int[] id_pregunta = {
            1, 1, 1, 1,
            2, 2, 2, 2,
            3, 3, 3, 3,
            4, 4, 4, 4,
            5, 5, 5, 5,
            6, 6, 6, 6,
            7, 7,
            8, 8,
            9, 9,
            10,
            11,
            12,
            13, 13,
            14, 14,
            15, 15
    };
    final String[] opcion = {
            "San salvador", "Chalatenango", "La paz", "La union",
            "Micologia", "Hongologia", "Otra", "Ambas",
            "Conica", "UTM", "Conforme", "Otra",
            "Eratostenes", "Aristoteles", "Pitagoras", "Newton",
            "Royer Tomlinson", "Royer Arias", "Arias Tomlinson", "Tomlinson Royer",
            "UTM", "Conforme de Lambert", "Conforme cónica de Lambert", "Cónica conforme de Lambert",
            "Marco", "Frederick",
            "Carlos", "Henry",
            "Estupinian", "Elton",
            "google",
            "apple",
            "microsoft",
            "Verdadero", "Falso",
            "Verdadero", "Falso",
            "Verdadero", "Falso"
    };
    final int[] correcta = {
            1, 0, 0, 0,
            1, 0, 0, 0,
            1, 0, 0, 0,
            1, 0, 0, 0,
            1, 0, 0, 0,
            0, 0, 0, 1,
            0, 1,
            0, 1,
            0, 1,
            1,
            1,
            1,
            1, 0,
            0, 1,
            1, 0
    };

    //Tabla Clave_area
    final int[] id_clave_area_pk = {1, 2, 3, 4};
    final int[] id_area_ca = {1, 3, 4, 2 };
    final int[] id_clave_ca = {1, 1, 1, 1};
    final int[] numero_preguntas = {6, 1, 3, 3};
    final int[] aleatorio = {1, 1, 1, 1};
    final int[] peso = {35, 30, 15, 20};

    //Tabla Clave_area_pregunta
    final int[] id_clave_area_pregunta_pk = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    final int[] id_pregunta_cap = {2, 6, 5, 4, 3, 1, 7, 9, 8, 12, 10, 11, 15, 13, 14};
    final int[] id_clave_area_cap = {1, 1, 1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4};

    //Tabla Pensum_materia
    final int[] id_pensum_materia_pk = {1, 2, 3, 4, 5};
    final int[] id_cat_mat_pm = {1, 2, 3, 4, 5};
    final int[] id_pensum_pm = {1, 1, 1, 1, 1};

    //Tabla Carga_academica
    final int[] id_carga_academica_pk = {1, 2, 3, 4, 5};
    final int[] id_mat_ci = {1, 2, 3, 4, 5};
    final int[] id_pdg_dcn = {1, 2, 3, 4, 5};
    final int[] id_grup_carg = {1, 1, 1, 1, 1};

    //Tabla DetalleIncEst
    final int[] id_detalle_insc_est_pk = {1, 2, 3, 4, 5};
    final int[] id_est = {1, 1, 1, 1, 1};
    final int[] id_carga_aca = {1, 2, 3, 4, 5};

    public void llenarBD(SQLiteDatabase db){

        //Tipo_item
        /*ContentValues c_tipo_item = new ContentValues();
        for (int i =0; i<nombre_tipo_item.length; i++) {
            c_tipo_item.put("id_tipo_item", id_tipo_item_pk[i]);
            c_tipo_item.put("nombre_tipo_item", nombre_tipo_item[i]);
            db.insert("tipo_item", null, c_tipo_item);
        }*/

        //Grupo_carga
        ContentValues c_grupo_carga = new ContentValues();
        for (int i =0; i<tipo.length; i++) {
            c_grupo_carga.put("tipo", tipo[i]);
            c_grupo_carga.put("id_grup_carg", id_grup_car_pk[i]);
            db.insert("grupo_carga", null, c_grupo_carga);
        }

        //Facultad
        ContentValues c_facultad = new ContentValues();
        for (int i =0; i<nombre_facultad.length; i++) {
            c_facultad.put("id_facultad", id_facultad_pk[i]);
            c_facultad.put("nombre_facultad", nombre_facultad[i]);
            db.insert("facultad", null, c_facultad);
        }

        //Escuela
        ContentValues c_escuela = new ContentValues();
        for (int i =0; i<nombre_escuela.length; i++) {
            c_escuela.put("id_escuela", id_escuela_pk[i]);
            c_escuela.put("id_facultad", id_facultad[i]);
            c_escuela.put("nombre_escuela", nombre_escuela[i]);
            c_escuela.put("codigo_escuela", codigo_escuela[i]);
            db.insert("escuela", null, c_escuela);
        }

        //Carrera
        ContentValues c_carrera = new ContentValues();
        for (int i =0; i<nombre_carrera.length; i++) {
            c_carrera.put("id_carrera", id_carrera_pk[i]);
            c_carrera.put("id_escuela", id_escuela[i]);
            c_carrera.put("nombre_carrera", nombre_carrera[i]);
            db.insert("carrera", null, c_carrera);
        }

        //Pensum
        ContentValues c_pensum = new ContentValues();
        for (int i =0; i<anio_pensum.length; i++) {
            c_pensum.put("id_penum", id_pensum_pk[i]);
            c_pensum.put("id_carrera", id_carrera[i]);
            c_pensum.put("anio_pensum", anio_pensum[i]);
            db.insert("pensum", null, c_pensum);
        }

        //Materia
        ContentValues c_materia = new ContentValues();
        for (int i =0; i<codigo_mat.length; i++) {
            c_materia.put("id_cat_mat", id_materia_pk[i]);
            c_materia.put("codigo_mat", codigo_mat[i]);
            c_materia.put("nombre_mar", nombre_mat[i]);
            c_materia.put("es_electiva", es_electiva[i]);
            c_materia.put("maximo_cant_preguntas", cantidad_maxima_preguntas[i]);
            db.insert("cat_mat_materia", null, c_materia);
        }

        //Usuario
        ContentValues c_usuario = new ContentValues();
        for (int i =0; i<nomusuario.length; i++) {
            c_usuario.put("idusuario", id_usuario_pk[i]);
            c_usuario.put("nomusuario", nomusuario[i]);
            c_usuario.put("clave", clave[i]);
            c_usuario.put("rol", rol[i]);
            db.insert("usuario", null, c_usuario);
        }

        //Docente
        ContentValues c_docente = new ContentValues();
        for (int i =0; i<carnet_dcn.length; i++) {
            c_docente.put("id_pdg_dcn", id_docente_pk[i]);
            c_docente.put("id_escuela", id_escuela_docente[i]);
            c_docente.put("carnet_dcn", carnet_dcn[i]);
            c_docente.put("anio_titulo", anio_titulacion[i]);
            c_docente.put("activo", activo[i]);
            c_docente.put("tipojornada", tipojornada[i]);
            c_docente.put("descripciondocente", descripciondocente[i]);
            c_docente.put("id_cargo_actual", id_cargo_actual[i]);
            c_docente.put("id_segundo_cargo", id_segundo_cargo[i]);
            c_docente.put("nombre_docente", nombre_docente[i]);
            c_docente.put("idusuario", idusuario[i]);
            db.insert("pdg_dcn_docente", null, c_docente);
        }

        //Estudiante
        ContentValues c_estudiante = new ContentValues();
        for (int i =0; i<carnet.length; i++) {
            c_estudiante.put("id_est", id_estudiante_pk[i]);
            c_estudiante.put("carnet", carnet[i]);
            c_estudiante.put("nombre", nombre[i]);
            c_estudiante.put("activo", activo_estudiante[i]);
            c_estudiante.put("anio_ingreso", anio_ingreso[i]);
            c_estudiante.put("idusuario", idusuario_estudiante[i]);
            db.insert("estudiante", null, c_estudiante);
        }

        //Materia_ciclo
        ContentValues c_materia_ciclo = new ContentValues();
        for (int i =0; i<ciclo.length; i++) {
            c_materia_ciclo.put("id_mat_ci", id_materia_ciclo_pk[i]);
            c_materia_ciclo.put("id_cat_mat", id_cat_mat[i]);
            c_materia_ciclo.put("anio", anio[i]);
            c_materia_ciclo.put("ciclo", ciclo[i]);
            db.insert("materia_ciclo", null, c_materia_ciclo);
        }

        //Encuesta
        ContentValues c_encuesta = new ContentValues();
        for (int i =0; i<titulo_encuesta.length; i++) {
            c_encuesta.put("id_encuesta", id_encuesta_pk[i]);
            c_encuesta.put("id_pdg_dcn", id_pdg_dcn_encuesta[i]);
            c_encuesta.put("titulo_encuesta", titulo_encuesta[i]);
            c_encuesta.put("descripcion_encuesta", descripcion_encuesta[i]);
            c_encuesta.put("fecha_inicio_encuesta", fecha_inicio_encuesta[i]);
            c_encuesta.put("fecha_final_encuesta", fecha_final_encuesta[i]);
            db.insert("encuesta", null, c_encuesta);
        }

        //Evaluacion
        ContentValues c_evaluacion = new ContentValues();
        for (int i =0; i<nombre_evaluacion.length; i++) {
            c_evaluacion.put("id_evaluacion", id_evaluacion_pk[i]);
            c_evaluacion.put("id_carg_aca", id_carga_aca[i]);
            c_evaluacion.put("duracion", duracion[i]);
            c_evaluacion.put("intento", intento[i]);
            c_evaluacion.put("nombre_evaluacion", nombre_evaluacion[i]);
            c_evaluacion.put("descripcion_evaluacion", descripcion_evaluacion[i]);
            db.insert("evaluacion", null, c_evaluacion);
        }

        //Turno
        ContentValues c_turno = new ContentValues();
        for (int i =0; i<contrasenia.length; i++) {
            c_turno.put("id_turno", id_turno_pk[i]);
            c_turno.put("id_evaluacion", id_evaluacion[i]);
            c_turno.put("fecha_inicio_turno", fecha_inicio_turno[i]);
            c_turno.put("fecha_final_turno", fecha_final_turno[i]);
            c_turno.put("visibilidad", visibilidad[i]);
            c_turno.put("contrasenia", contrasenia[i]);
            db.insert("turno", null, c_turno);
        }

        //Clave
        ContentValues c_clave = new ContentValues();
        for (int i =0; i<numero_clave.length; i++) {
            c_clave.put("id_clave", id_clave_pk[i]);
            c_clave.put("id_turno", id_turno[i]);
            c_clave.put("id_encuesta", id_encuesta[i]);
            c_clave.put("numero_clave", numero_clave[i]);
            db.insert("clave", null, c_clave);
        }

        //Area
        ContentValues c_area = new ContentValues();
        for (int i =0; i<titulo.length; i++) {
            c_area.put("id_area", id_area_pk[i]);
            c_area.put("id_cat_mat", id_cat_mat_area[i]);
            c_area.put("id_pdg_dcn", id_pdg_dcn_area[i]);
            c_area.put("id_tipo_item", id_tipo_item[i]);
            c_area.put("titulo", titulo[i]);
            db.insert("area", null, c_area);
        }

        //Emparejamiento
        ContentValues c_emparejamiento = new ContentValues();
        for (int i =0; i<id_area.length; i++) {
            c_emparejamiento.put("id_grupo_emp", id_emparejamiento_pk[i]);
            c_emparejamiento.put("id_area", id_area[i]);
            c_emparejamiento.put("descripcion_grupo_emp", descripcion_grupo_emp[i]);
            db.insert("grupo_emparejamiento", null, c_emparejamiento);
        }

        //Pregunta
        ContentValues c_pregunta = new ContentValues();
        for (int i =0; i<pregunta.length; i++) {
            c_pregunta.put("id_pregunta", id_pregunta_pk[i]);
            c_pregunta.put("id_grupo_emp", id_grupo_emp[i]);
            c_pregunta.put("pregunta", pregunta[i]);
            db.insert("pregunta", null, c_pregunta);
        }

        //Opcion
        ContentValues c_opcion = new ContentValues();
        for (int i =0; i<opcion.length; i++) {
            c_opcion.put("id_opcion", id_opcion_pk[i]);
            c_opcion.put("id_pregunta", id_pregunta[i]);
            c_opcion.put("opcion", opcion[i]);
            c_opcion.put("correcta", correcta[i]);
            db.insert("opcion", null, c_opcion);
        }

        //Clave_area
        ContentValues c_clave_area = new ContentValues();
        for (int i =0; i<numero_preguntas.length; i++) {
            c_clave_area.put("id_clave_area", id_clave_area_pk[i]);
            c_clave_area.put("id_area", id_area_ca[i]);
            c_clave_area.put("id_clave", id_clave_ca[i]);
            c_clave_area.put("numero_preguntas", numero_preguntas[i]);
            c_clave_area.put("aleatorio", aleatorio[i]);
            c_clave_area.put("peso", peso[i]);
            db.insert("clave_area", null, c_clave_area);
        }

        //Clavev_area_pregunta
        ContentValues c_clave_area_pregunta = new ContentValues();
        for (int i =0; i<id_pregunta_cap.length; i++) {
            c_clave_area_pregunta.put("id_clave_area_pre", id_clave_area_pregunta_pk[i]);
            c_clave_area_pregunta.put("id_pregunta", id_pregunta_cap[i]);
            c_clave_area_pregunta.put("id_clave_area", id_clave_area_cap[i]);
            db.insert("clave_area_pregunta", null, c_clave_area_pregunta);
        }

        //Pensum_materia
        ContentValues c_pensum_materia = new ContentValues();
        for (int i =0; i<id_pensum_pm.length; i++) {
            c_pensum_materia.put("id_pensum_materia", id_pensum_materia_pk[i]);
            c_pensum_materia.put("id_penum", id_pensum_pm[i]);
            c_pensum_materia.put("id_cat_mat", id_cat_mat_pm[i]);
            db.insert("pensum_materia", null, c_pensum_materia);
        }

        //Carga_academica
        ContentValues c_carga_academica = new ContentValues();
        for (int i =0; i<id_mat_ci.length; i++) {
            c_carga_academica.put("id_carg_aca", id_carga_academica_pk[i]);
            c_carga_academica.put("id_mat_ci", id_mat_ci[i]);
            c_carga_academica.put("id_pdg_dcn", id_pdg_dcn[i]);
            c_carga_academica.put("id_grup_carg", id_grup_carg[i]);
            db.insert("carga_academica", null, c_carga_academica);
        }

        //DetalleInscEst
        ContentValues c_detalle_inscripcion_estudiante = new ContentValues();
        for (int i =0; i<id_est.length; i++) {
            c_detalle_inscripcion_estudiante.put("id_det_insc", id_detalle_insc_est_pk[i]);
            c_detalle_inscripcion_estudiante.put("id_est", id_est[i]);
            c_detalle_inscripcion_estudiante.put("id_carg_aca", id_carga_aca[i]);
            db.insert("detalleinscest", null, c_detalle_inscripcion_estudiante);
        }
    }

    public void vaciarBD(SQLiteDatabase db){
        db.execSQL("delete from area");
        db.execSQL("delete from carga_academica");
        db.execSQL("delete from carrera");
        db.execSQL("delete from cat_mat_materia;");
        db.execSQL("delete from clave");
        db.execSQL("delete from clave_area");
        db.execSQL("delete from clave_area_pregunta");
        db.execSQL("delete from detalleinscest");
        db.execSQL("delete from encuesta");
        db.execSQL("delete from encuestado");
        db.execSQL("delete from escuela");
        db.execSQL("delete from estudiante");
        db.execSQL("delete from evaluacion");
        db.execSQL("delete from facultad");
        db.execSQL("delete from grupo_carga");
        db.execSQL("delete from grupo_emparejamiento");
        db.execSQL("delete from intento");
        db.execSQL("delete from materia_ciclo");
        db.execSQL("delete from opcion");
        db.execSQL("delete from pdg_dcn_docente");
        db.execSQL("delete from pensum");
        db.execSQL("delete from pensum_materia");
        db.execSQL("delete from pregunta");
        db.execSQL("delete from respuesta");
        db.execSQL("delete from turno");
        //db.execSQL("delete from tipo_item");
        db.execSQL("delete from sesionusuario");
        db.execSQL("delete from usuario");
    }
}
