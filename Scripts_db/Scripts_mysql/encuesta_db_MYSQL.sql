/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     11/06/2019 11:04:59                          */
/*==============================================================*/

DROP TABLE IF EXISTS DETALLEINSCEST;
DROP TABLE IF EXISTS PENSUM_MATERIA;
DROP TABLE IF EXISTS CLAVE_AREA_PREGUNTA;
DROP TABLE IF EXISTS CLAVE_AREA;
DROP TABLE IF EXISTS RESPUESTA;
DROP TABLE IF EXISTS OPCION;
DROP TABLE IF EXISTS PREGUNTA;
DROP TABLE IF EXISTS GRUPO_EMPAREJAMIENTO;
DROP TABLE IF EXISTS INTENTO;
DROP TABLE IF EXISTS CLAVE;
DROP TABLE IF EXISTS AREA;
DROP TABLE IF EXISTS TURNO;
DROP TABLE IF EXISTS EVALUACION;
DROP TABLE IF EXISTS ENCUESTA;
DROP TABLE IF EXISTS CARGA_ACADEMICA;
DROP TABLE IF EXISTS MATERIA_CICLO;
DROP TABLE IF EXISTS ESTUDIANTE;
DROP TABLE IF EXISTS PDG_DCN_DOCENTE;
DROP TABLE IF EXISTS USUARIO;
DROP TABLE IF EXISTS CAT_MAT_MATERIA;
DROP TABLE IF EXISTS PENSUM;
DROP TABLE IF EXISTS CARRERA;
DROP TABLE IF EXISTS ESCUELA;
DROP TABLE IF EXISTS FACULTAD;
DROP TABLE IF EXISTS GRUPO_CARGA;
DROP TABLE IF EXISTS TIPO_ITEM;
DROP TABLE IF EXISTS ENCUESTADO;
DROP TABLE IF EXISTS SESIONUSUARIO;


/*==============================================================*/
/* Table: AREA                                                  */
/*==============================================================*/
create table AREA
(
   ID_AREA              int not null,
   ID_CAT_MAT           int,
   ID_PDG_DCN           int,
   ID_TIPO_ITEM         int not null,
   TITULO               varchar(100) not null,
   primary key (ID_AREA)
);

/*==============================================================*/
/* Table: CARGA_ACADEMICA                                       */
/*==============================================================*/
create table CARGA_ACADEMICA
(
   ID_CARG_ACA          int not null,
   ID_MAT_CI            int,
   ID_PDG_DCN           int,
   ID_GRUP_CARG         int,
   primary key (ID_CARG_ACA)
);

/*==============================================================*/
/* Table: CARRERA                                               */
/*==============================================================*/
create table CARRERA
(
   ID_CARRERA           int not null,
   ID_ESCUELA           int,
   NOMBRE_CARRERA       varchar(100) not null,
   primary key (ID_CARRERA)
);

/*==============================================================*/
/* Table: CAT_MAT_MATERIA                                       */
/*==============================================================*/
create table CAT_MAT_MATERIA
(
   ID_CAT_MAT           int not null,
   CODIGO_MAT           varchar(45) not null,
   NOMBRE_MAR           varchar(100) not null,
   ES_ELECTIVA          int not null,
   MAXIMO_CANT_PREGUNTAS int not null,
   primary key (ID_CAT_MAT)
);

/*==============================================================*/
/* Table: CLAVE                                                 */
/*==============================================================*/
create table CLAVE
(
   ID_CLAVE             int not null,
   ID_TURNO             int,
   ID_ENCUESTA          int,
   NUMERO_CLAVE         int,
   primary key (ID_CLAVE)
);

/*==============================================================*/
/* Table: CLAVE_AREA                                            */
/*==============================================================*/
create table CLAVE_AREA
(
   ID_CLAVE_AREA        int not null,
   ID_AREA              int not null,
   ID_CLAVE             int not null,
   NUMERO_PREGUNTAS     int,
   ALEATORIO            bool,
   PESO                 int not null,
   primary key (ID_CLAVE_AREA)
);

/*==============================================================*/
/* Table: CLAVE_AREA_PREGUNTA                                   */
/*==============================================================*/
create table CLAVE_AREA_PREGUNTA
(
   ID_CLAVE_AREA_PRE    int not null,
   ID_PREGUNTA          int not null,
   ID_CLAVE_AREA        int,
   primary key (ID_CLAVE_AREA_PRE)
);

/*==============================================================*/
/* Table: DETALLEINSCEST                                        */
/*==============================================================*/
create table DETALLEINSCEST
(
   ID_DET_INSC          int not null,
   ID_EST               int,
   ID_CARG_ACA          int,
   primary key (ID_DET_INSC)
);

/*==============================================================*/
/* Table: ENCUESTA                                              */
/*==============================================================*/
create table ENCUESTA
(
   ID_ENCUESTA          int not null,
   ID_PDG_DCN           int not null,
   TITULO_ENCUESTA      varchar(100) not null,
   DESCRIPCION_ENCUESTA text,
   FECHA_INICIO_ENCUESTA datetime not null,
   FECHA_FINAL_ENCUESTA datetime not null,
   primary key (ID_ENCUESTA)
);

/*==============================================================*/
/* Table: ENCUESTADO                                            */
/*==============================================================*/
create table ENCUESTADO
(
   ID                   varchar(50) not null,
   primary key (ID)
);

/*==============================================================*/
/* Table: ESCUELA                                               */
/*==============================================================*/
create table ESCUELA
(
   ID_ESCUELA           int not null,
   ID_FACULTAD          int not null,
   NOMBRE_ESCUELA       varchar(100) not null,
   CODIGO_ESCUELA       varchar(20) not null,
   primary key (ID_ESCUELA)
);

/*==============================================================*/
/* Table: ESTUDIANTE                                            */
/*==============================================================*/
create table ESTUDIANTE
(
   ID_EST               int not null,
   IDUSUARIO            char(2),
   CARNET               varchar(10) not null,
   NOMBRE               varchar(100) not null,
   ACTIVO               int not null,
   ANIO_INGRESO         varchar(4) not null,
   primary key (ID_EST)
);

/*==============================================================*/
/* Table: EVALUACION                                            */
/*==============================================================*/
create table EVALUACION
(
   ID_EVALUACION        int not null,
   ID_CARG_ACA          int not null,
   DURACION             int not null,
   INTENTO              int not null,
   NOMBRE_EVALUACION    varchar(100) not null,
   DESCRIPCION_EVALUACION text,
   primary key (ID_EVALUACION)
);

/*==============================================================*/
/* Table: FACULTAD                                              */
/*==============================================================*/
create table FACULTAD
(
   ID_FACULTAD          int not null,
   NOMBRE_FACULTAD      varchar(100) not null,
   primary key (ID_FACULTAD)
);

/*==============================================================*/
/* Table: GRUPO_CARGA                                           */
/*==============================================================*/
create table GRUPO_CARGA
(
   ID_GRUP_CARG         int not null,
   TIPO                 varchar(15) not null,
   primary key (ID_GRUP_CARG)
);

/*==============================================================*/
/* Table: GRUPO_EMPAREJAMIENTO                                  */
/*==============================================================*/
create table GRUPO_EMPAREJAMIENTO
(
   ID_GRUPO_EMP         int not null,
   ID_AREA              int not null,
   DESCRIPCION_GRUPO_EMP varchar(100),
   primary key (ID_GRUPO_EMP)
);

/*==============================================================*/
/* Table: INTENTO                                               */
/*==============================================================*/
create table INTENTO
(
   ID_INTENTO           int not null AUTO_INCREMENT,
   ID_EST               int,
   ID_CLAVE             int,
   ID                   varchar(50),
   FECHA_INICIO_INTENTO timestamp,
   FECHA_FINAL_INTENTO  timestamp null,
   NUMERO_INTENTO       int,
   NOTA_INTENTO         decimal(5,2),
   primary key (ID_INTENTO)
);

/*==============================================================*/
/* Table: MATERIA_CICLO                                         */
/*==============================================================*/
create table MATERIA_CICLO
(
   ID_MAT_CI            int not null,
   ID_CAT_MAT           int,
   CICLO                varchar(45) not null,
   ANIO                 varchar(4) not null,
   primary key (ID_MAT_CI)
);

/*==============================================================*/
/* Table: OPCION                                                */
/*==============================================================*/
create table OPCION
(
   ID_OPCION            int not null,
   ID_PREGUNTA          int not null,
   OPCION               text not null,
   CORRECTA             bool not null,
   primary key (ID_OPCION)
);

/*==============================================================*/
/* Table: PDG_DCN_DOCENTE                                       */
/*==============================================================*/
create table PDG_DCN_DOCENTE
(
   ID_PDG_DCN           int not null,
   ID_ESCUELA           int,
   IDUSUARIO            char(2),
   CARNET_DCN           varchar(45) not null,
   ANIO_TITULO          varchar(45) default NULL,
   ACTIVO               int not null,
   TIPOJORNADA          int default NULL comment '1-Completo, 2-Parcial',
   DESCRIPCIONDOCENTE   varchar(800) default NULL,
   ID_CARGO_ACTUAL      int default 1,
   ID_SEGUNDO_CARGO     int default NULL,
   NOMBRE_DOCENTE       varchar(150) not null,
   primary key (ID_PDG_DCN)
);

/*==============================================================*/
/* Table: PENSUM                                                */
/*==============================================================*/
create table PENSUM
(
   ID_PENUM             int not null,
   ID_CARRERA           int not null,
   ANIO_PENSUM          int not null,
   primary key (ID_PENUM)
);

/*==============================================================*/
/* Table: PENSUM_MATERIA                                        */
/*==============================================================*/
create table PENSUM_MATERIA
(
   ID_PENSUM_MATERIA    int not null,
   ID_CAT_MAT           int,
   ID_PENUM             int,
   primary key (ID_PENSUM_MATERIA)
);

/*==============================================================*/
/* Table: PREGUNTA                                              */
/*==============================================================*/
create table PREGUNTA
(
   ID_PREGUNTA          int not null,
   ID_GRUPO_EMP         int not null,
   PREGUNTA             text not null,
   primary key (ID_PREGUNTA)
);

/*==============================================================*/
/* Table: RESPUESTA                                             */
/*==============================================================*/
create table RESPUESTA
(
   ID_RESPUESTA         int not null AUTO_INCREMENT,
   ID_OPCION            int not null,
   ID_INTENTO           int not null,
   ID_PREGUNTA          int,
   TEXTO_RESPUESTA      varchar(30),
   primary key (ID_RESPUESTA)
);

/*==============================================================*/
/* Table: SESIONUSUARIO                                         */
/*==============================================================*/
create table SESIONUSUARIO
(
   INSESION             bool,
   IDSESION             int not null,
   IDUSUARIO            char(2),
   primary key (IDSESION)
);

/*==============================================================*/
/* Table: TIPO_ITEM                                             */
/*==============================================================*/
create table TIPO_ITEM
(
   ID_TIPO_ITEM         int not null,
   NOMBRE_TIPO_ITEM     varchar(100),
   primary key (ID_TIPO_ITEM)
);

/*==============================================================*/
/* Table: TURNO                                                 */
/*==============================================================*/
create table TURNO
(
   ID_TURNO             int not null,
   ID_EVALUACION        int not null,
   FECHA_INICIO_TURNO   datetime not null,
   FECHA_FINAL_TURNO    datetime not null,
   VISIBILIDAD          bool not null,
   CONTRASENIA          varchar(50) not null,
   primary key (ID_TURNO)
);

/*==============================================================*/
/* Table: USUARIO                                               */
/*==============================================================*/
create table USUARIO
(
   IDUSUARIO            char(2) not null,
   NOMUSUARIO           varchar(30),
   CLAVE                char(5),
   ROL                  int,
   primary key (IDUSUARIO)
);

alter table AREA add constraint FK_RELATIONSHIP_19 foreign key (ID_TIPO_ITEM)
      references TIPO_ITEM (ID_TIPO_ITEM) on delete restrict on update restrict;

alter table AREA add constraint FK_RELATIONSHIP_34 foreign key (ID_CAT_MAT)
      references CAT_MAT_MATERIA (ID_CAT_MAT) on delete restrict on update restrict;

alter table AREA add constraint FK_RELATIONSHIP_35 foreign key (ID_PDG_DCN)
      references PDG_DCN_DOCENTE (ID_PDG_DCN) on delete restrict on update restrict;

alter table CARGA_ACADEMICA add constraint FK_DOCENTE_CARGA_ACAD foreign key (ID_PDG_DCN)
      references PDG_DCN_DOCENTE (ID_PDG_DCN) on delete restrict on update restrict;

alter table CARGA_ACADEMICA add constraint FK_MAT_CICLO_CARGA_ACAD foreign key (ID_MAT_CI)
      references MATERIA_CICLO (ID_MAT_CI) on delete restrict on update restrict;

alter table CARGA_ACADEMICA add constraint FK_RELATIONSHIP_40 foreign key (ID_GRUP_CARG)
      references GRUPO_CARGA (ID_GRUP_CARG) on delete restrict on update restrict;

alter table CARRERA add constraint FK_FK_ESCUELA_CARRERA foreign key (ID_ESCUELA)
      references ESCUELA (ID_ESCUELA) on delete restrict on update restrict;

alter table CLAVE add constraint FK_RELATIONSHIP_33 foreign key (ID_ENCUESTA)
      references ENCUESTA (ID_ENCUESTA) on delete restrict on update restrict;

alter table CLAVE add constraint FK_RELATIONSHIP_36 foreign key (ID_TURNO)
      references TURNO (ID_TURNO) on delete restrict on update restrict;

alter table CLAVE_AREA add constraint FK_RELATIONSHIP_25 foreign key (ID_CLAVE)
      references CLAVE (ID_CLAVE) on delete restrict on update restrict;

alter table CLAVE_AREA add constraint FK_RELATIONSHIP_26 foreign key (ID_AREA)
      references AREA (ID_AREA) on delete restrict on update restrict;

alter table CLAVE_AREA_PREGUNTA add constraint FK_RELATIONSHIP_27 foreign key (ID_CLAVE_AREA)
      references CLAVE_AREA (ID_CLAVE_AREA) on delete restrict on update restrict;

alter table CLAVE_AREA_PREGUNTA add constraint FK_RELATIONSHIP_28 foreign key (ID_PREGUNTA)
      references PREGUNTA (ID_PREGUNTA) on delete restrict on update restrict;

alter table DETALLEINSCEST add constraint FK_RELATIONSHIP_18 foreign key (ID_EST)
      references ESTUDIANTE (ID_EST) on delete restrict on update restrict;

alter table DETALLEINSCEST add constraint FK_RELATIONSHIP_45 foreign key (ID_CARG_ACA)
      references CARGA_ACADEMICA (ID_CARG_ACA) on delete restrict on update restrict;

alter table ENCUESTA add constraint FK_RELATIONSHIP_37 foreign key (ID_PDG_DCN)
      references PDG_DCN_DOCENTE (ID_PDG_DCN) on delete restrict on update restrict;

alter table ESCUELA add constraint FK_RELATIONSHIP_41 foreign key (ID_FACULTAD)
      references FACULTAD (ID_FACULTAD) on delete restrict on update restrict;

alter table ESTUDIANTE add constraint FK_RELATIONSHIP_49 foreign key (IDUSUARIO)
      references USUARIO (IDUSUARIO) on delete restrict on update restrict;

alter table EVALUACION add constraint FK_RELATIONSHIP_14 foreign key (ID_CARG_ACA)
      references CARGA_ACADEMICA (ID_CARG_ACA) on delete restrict on update restrict;

alter table GRUPO_EMPAREJAMIENTO add constraint FK_RELATIONSHIP_38 foreign key (ID_AREA)
      references AREA (ID_AREA) on delete restrict on update restrict;

alter table INTENTO add constraint FK_RELATIONSHIP_23 foreign key (ID_EST)
      references ESTUDIANTE (ID_EST) on delete restrict on update restrict;

alter table INTENTO add constraint FK_RELATIONSHIP_30 foreign key (ID)
      references ENCUESTADO (ID) on delete restrict on update restrict;

alter table INTENTO add constraint FK_RELATIONSHIP_32 foreign key (ID_CLAVE)
      references CLAVE (ID_CLAVE) on delete restrict on update restrict;

alter table MATERIA_CICLO add constraint FK_RELATIONSHIP_47 foreign key (ID_CAT_MAT)
      references CAT_MAT_MATERIA (ID_CAT_MAT) on delete restrict on update restrict;

alter table OPCION add constraint FK_RELATIONSHIP_21 foreign key (ID_PREGUNTA)
      references PREGUNTA (ID_PREGUNTA) on delete restrict on update restrict;

alter table PDG_DCN_DOCENTE add constraint FK_FK_ESCUELA_PDG_DCN_DOCENTE foreign key (ID_ESCUELA)
      references ESCUELA (ID_ESCUELA) on delete restrict on update restrict;

alter table PDG_DCN_DOCENTE add constraint FK_RELATIONSHIP_50 foreign key (IDUSUARIO)
      references USUARIO (IDUSUARIO) on delete restrict on update restrict;

alter table PENSUM add constraint FK_RELATIONSHIP_42 foreign key (ID_CARRERA)
      references CARRERA (ID_CARRERA) on delete restrict on update restrict;

alter table PENSUM_MATERIA add constraint FK_RELATIONSHIP_43 foreign key (ID_PENUM)
      references PENSUM (ID_PENUM) on delete restrict on update restrict;

alter table PENSUM_MATERIA add constraint FK_RELATIONSHIP_44 foreign key (ID_CAT_MAT)
      references CAT_MAT_MATERIA (ID_CAT_MAT) on delete restrict on update restrict;

alter table PREGUNTA add constraint FK_RELATIONSHIP_24 foreign key (ID_GRUPO_EMP)
      references GRUPO_EMPAREJAMIENTO (ID_GRUPO_EMP) on delete restrict on update restrict;

alter table RESPUESTA add constraint FK_RELATIONSHIP_29 foreign key (ID_INTENTO)
      references INTENTO (ID_INTENTO) on delete restrict on update restrict;

alter table RESPUESTA add constraint FK_RELATIONSHIP_31 foreign key (ID_OPCION)
      references OPCION (ID_OPCION) on delete restrict on update restrict;

alter table RESPUESTA add constraint FK_RELATIONSHIP_46 foreign key (ID_PREGUNTA)
      references PREGUNTA (ID_PREGUNTA) on delete restrict on update restrict;

alter table SESIONUSUARIO add constraint FK_RELATIONSHIP_48 foreign key (IDUSUARIO)
      references USUARIO (IDUSUARIO) on delete restrict on update restrict;

alter table TURNO add constraint FK_RELATIONSHIP_39 foreign key (ID_EVALUACION)
      references EVALUACION (ID_EVALUACION) on delete restrict on update restrict;

