--==============================================================
-- DBMS name:      SQLITE_3
-- Created on:     10/5/2019 11:37:28
--==============================================================

create table TIPO_ITEM (
ID_TIPO_ITEM         INTEGER              not null,
NOMBRE_TIPO_ITEM     VARCHAR(100),
primary key (ID_TIPO_ITEM));

create table CAT_MAT_MATERIA (
ID_CAT_MAT           INTEGER              not null,
CODIGO_MAT           VARCHAR(45)          not null,
NOMBRE_MAR           VARCHAR(100)         not null,
ES_ELECTIVA          INTEGER              not null,
MAXIMO_CANT_PREGUNTAS INTEGER              not null,
primary key (ID_CAT_MAT));

create table FACULTAD (
ID_FACULTAD          INTEGER              not null,
NOMBRE_FACULTAD      VARCHAR(100)         not null,
primary key (ID_FACULTAD));

create table ESCUELA (
ID_ESCUELA           INTEGER              not null,
ID_FACULTAD          INTEGER              not null,
NOMBRE_ESCUELA       VARCHAR(100)         not null,
CODIGO_ESCUELA       VARCHAR(20)          not null,
primary key (ID_ESCUELA),
foreign key (ID_FACULTAD)
      references FACULTAD (ID_FACULTAD));

create table USUARIO (
IDUSUARIO            CHAR(2)              not null,
NOMUSUARIO           VARCHAR(30),
CLAVE                CHAR(5),
ROL                  INTEGER,
primary key (IDUSUARIO));

create table PDG_DCN_DOCENTE (
ID_PDG_DCN           INTEGER              not null,
ID_ESCUELA           INTEGER,
IDUSUARIO            CHAR(2),
CARNET_DCN           VARCHAR(45)          not null,
ANIO_TITULO          VARCHAR(45)           default NULL,
ACTIVO               INTEGER              not null default NULL,
TIPOJORNADA          INTEGER               default NULL,
DESCRIPCIONDOCENTE   VARCHAR(800)          default NULL,
ID_CARGO_ACTUAL      INTEGER               default 1,
ID_SEGUNDO_CARGO     INTEGER               default NULL,
NOMBRE_DOCENTE       VARCHAR(150)         not null,
primary key (ID_PDG_DCN),
foreign key (ID_ESCUELA)
      references ESCUELA (ID_ESCUELA),
foreign key (IDUSUARIO)
      references USUARIO (IDUSUARIO));

create table AREA (
ID_AREA              INTEGER              not null,
ID_CAT_MAT           INTEGER,
ID_PDG_DCN           INTEGER,
ID_TIPO_ITEM         INTEGER              not null,
TITULO               VARCHAR(100)         not null,
primary key (ID_AREA),
foreign key (ID_TIPO_ITEM)
      references TIPO_ITEM (ID_TIPO_ITEM),
foreign key (ID_CAT_MAT)
      references CAT_MAT_MATERIA (ID_CAT_MAT),
foreign key (ID_PDG_DCN)
      references PDG_DCN_DOCENTE (ID_PDG_DCN));

create table MATERIA_CICLO (
ID_MAT_CI            INTEGER              not null,
ID_CAT_MAT           INTEGER,
CICLO                VARCHAR(45)          not null default NULL,
ANIO                 VARCHAR(4)           not null,
primary key (ID_MAT_CI),
foreign key (ID_CAT_MAT)
      references CAT_MAT_MATERIA (ID_CAT_MAT));

create table GRUPO_CARGA (
ID_GRUP_CARG         INTEGER              not null,
TIPO                 VARCHAR(15)          not null,
primary key (ID_GRUP_CARG));

create table CARGA_ACADEMICA (
ID_CARG_ACA          INTEGER              not null,
ID_MAT_CI            INTEGER,
ID_PDG_DCN           INTEGER,
ID_GRUP_CARG         INTEGER,
primary key (ID_CARG_ACA),
foreign key (ID_PDG_DCN)
      references PDG_DCN_DOCENTE (ID_PDG_DCN),
foreign key (ID_MAT_CI)
      references MATERIA_CICLO (ID_MAT_CI),
foreign key (ID_GRUP_CARG)
      references GRUPO_CARGA (ID_GRUP_CARG));

create table CARRERA (
ID_CARRERA           INTEGER              not null,
ID_ESCUELA           INTEGER,
NOMBRE_CARRERA       VARCHAR(100)         not null,
primary key (ID_CARRERA),
foreign key (ID_ESCUELA)
      references ESCUELA (ID_ESCUELA));

create table ENCUESTA (
ID_ENCUESTA          INTEGER              not null,
ID_PDG_DCN           INTEGER              not null,
TITULO_ENCUESTA      VARCHAR(100)         not null,
DESCRIPCION_ENCUESTA CHAR(254),
FECHA_INICIO_ENCUESTA DATE                 not null,
FECHA_FINAL_ENCUESTA DATE                 not null,
primary key (ID_ENCUESTA),
foreign key (ID_PDG_DCN)
      references PDG_DCN_DOCENTE (ID_PDG_DCN));

create table EVALUACION (
ID_EVALUACION        INTEGER              not null,
ID_CARG_ACA          INTEGER              not null,
DURACION             INTEGER              not null,
INTENTO              INTEGER              not null,
NOMBRE_EVALUACION    VARCHAR(100)         not null,
DESCRIPCION_EVALUACION CHAR(254),
primary key (ID_EVALUACION),
foreign key (ID_CARG_ACA)
      references CARGA_ACADEMICA (ID_CARG_ACA));

create table TURNO (
ID_TURNO             INTEGER              not null,
ID_EVALUACION        INTEGER              not null,
FECHA_INICIO_TURNO   DATE                 not null,
FECHA_FINAL_TURNO    DATE                 not null,
VISIBILIDAD          smallint             not null,
CONTRASENIA          VARCHAR(50)          not null,
primary key (ID_TURNO),
foreign key (ID_EVALUACION)
      references EVALUACION (ID_EVALUACION));

create table CLAVE (
ID_CLAVE             INTEGER              not null,
ID_TURNO             INTEGER,
ID_ENCUESTA          INTEGER              not null,
NUMERO_CLAVE         INTEGER,
primary key (ID_CLAVE),
foreign key (ID_ENCUESTA)
      references ENCUESTA (ID_ENCUESTA),
foreign key (ID_TURNO)
      references TURNO (ID_TURNO));

create table CLAVE_AREA (
ID_CLAVE_AREA        INTEGER              not null,
ID_AREA              INTEGER              not null,
ID_CLAVE             INTEGER              not null,
NUMERO_PREGUNTAS     INTEGER,
ALEATORIO            smallint,
PESO                 INTEGER              not null,
primary key (ID_CLAVE_AREA),
foreign key (ID_CLAVE)
      references CLAVE (ID_CLAVE),
foreign key (ID_AREA)
      references AREA (ID_AREA));

create table GRUPO_EMPAREJAMIENTO (
ID_GRUPO_EMP         INTEGER              not null,
ID_AREA              INTEGER              not null,
DESCRIPCION_GRUPO_EMP VARCHAR(100),
primary key (ID_GRUPO_EMP),
foreign key (ID_AREA)
      references AREA (ID_AREA));

create table PREGUNTA (
ID_PREGUNTA          INTEGER              not null,
ID_GRUPO_EMP         INTEGER              not null,
PREGUNTA             CHAR(254)            not null,
primary key (ID_PREGUNTA),
foreign key (ID_GRUPO_EMP)
      references GRUPO_EMPAREJAMIENTO (ID_GRUPO_EMP));

create table CLAVE_AREA_PREGUNTA (
ID_CLAVE_AREA_PRE    INTEGER              not null,
ID_PREGUNTA          INTEGER              not null,
ID_CLAVE_AREA        INTEGER,
primary key (ID_CLAVE_AREA_PRE),
foreign key (ID_CLAVE_AREA)
      references CLAVE_AREA (ID_CLAVE_AREA),
foreign key (ID_PREGUNTA)
      references PREGUNTA (ID_PREGUNTA));

create table ESTUDIANTE (
ID_EST               INTEGER              not null,
IDUSUARIO            CHAR(2),
CARNET               VARCHAR(10)          not null,
NOMBRE               VARCHAR(100)         not null,
ACTIVO               INTEGER              not null,
ANIO_INGRESO         VARCHAR(4)           not null,
primary key (ID_EST),
foreign key (IDUSUARIO)
      references USUARIO (IDUSUARIO));

create table DETALLEINSCEST (
ID_DET_INSC          INTEGER              not null,
ID_EST               INTEGER,
ID_CARG_ACA          INTEGER,
primary key (ID_DET_INSC),
foreign key (ID_EST)
      references ESTUDIANTE (ID_EST),
foreign key (ID_CARG_ACA)
      references CARGA_ACADEMICA (ID_CARG_ACA));

create table ENCUESTADO (
ID                   VARCHAR(50)          not null,
primary key (ID));

create table INTENTO (
ID_INTENTO           INTEGER              not null,
ID_EST               INTEGER,
ID_CLAVE             INTEGER,
ID                   VARCHAR(50)          not null,
FECHA_INICIO_INTENTO DATE,
FECHA_FINAL_INTENTO  DATE,
NUMERO_INTENTO       INTEGER,
NOTA_INTENTO         DECIMAL(2,2),
primary key (ID_INTENTO),
foreign key (ID_EST)
      references ESTUDIANTE (ID_EST),
foreign key (ID_CLAVE)
      references CLAVE (ID_CLAVE),
foreign key (ID)
      references ENCUESTADO (ID);

create table OPCION (
ID_OPCION            INTEGER              not null,
ID_PREGUNTA          INTEGER              not null,
OPCION               CHAR(254)            not null,
CORRECTA             smallint             not null,
primary key (ID_OPCION),
foreign key (ID_PREGUNTA)
      references PREGUNTA (ID_PREGUNTA));

create table PENSUM (
ID_PENUM             INTEGER              not null,
ID_CARRERA           INTEGER              not null,
ANIO_PENSUM          INTEGER              not null,
primary key (ID_PENUM),
foreign key (ID_CARRERA)
      references CARRERA (ID_CARRERA));

create table PENSUM_MATERIA (
ID_PENSUM_MATERIA    INTEGER              not null,
ID_CAT_MAT           INTEGER,
ID_PENUM             INTEGER,
primary key (ID_PENSUM_MATERIA),
foreign key (ID_PENUM)
      references PENSUM (ID_PENUM),
foreign key (ID_CAT_MAT)
      references CAT_MAT_MATERIA (ID_CAT_MAT));

create table RESPUESTA (
ID_RESPUESTA         INTEGER              not null,
ID_OPCION            INTEGER              not null,
ID_INTENTO           INTEGER              not null,
ID_PREGUNTA          INTEGER,
TEXTO_RESPUESTA      VARCHAR(30),
primary key (ID_RESPUESTA),
foreign key (ID_INTENTO)
      references INTENTO (ID_INTENTO),
foreign key (ID_OPCION)
      references OPCION (ID_OPCION),
foreign key (ID_PREGUNTA)
      references PREGUNTA (ID_PREGUNTA));

create table SESIONUSUARIO (
INSESION             smallint,
IDSESION             INTEGER              not null,
IDUSUARIO            CHAR(2),
primary key (IDSESION),
foreign key (IDUSUARIO)
      references USUARIO (IDUSUARIO));

create unique index AREA_PK on AREA (ID_AREA ASC);
create  index RELATIONSHIP_19_FK on AREA (ID_TIPO_ITEM ASC);
create  index RELATIONSHIP_34_FK on AREA (ID_CAT_MAT ASC);
create  index RELATIONSHIP_35_FK on AREA (ID_PDG_DCN ASC);
create unique index CARGA_ACADEMICA_PK on CARGA_ACADEMICA (ID_CARG_ACA ASC);
create  index DOCENTE_CARGA_ACAD_FK on CARGA_ACADEMICA (ID_PDG_DCN ASC);
create  index MAT_CICLO_CARGA_ACAD_FK on CARGA_ACADEMICA (ID_MAT_CI ASC);
create  index RELATIONSHIP_40_FK on CARGA_ACADEMICA (ID_GRUP_CARG ASC);
create unique index CARRERA_PK on CARRERA (ID_CARRERA ASC);
create  index FK_ESCUELA_CARRERA_FK on CARRERA (ID_ESCUELA ASC);
create unique index CAT_MAT_MATERIA_PK on CAT_MAT_MATERIA (ID_CAT_MAT ASC);
create unique index CLAVE_PK on CLAVE (ID_CLAVE ASC);
create  index RELATIONSHIP_33_FK on CLAVE (ID_ENCUESTA ASC);
create  index RELATIONSHIP_36_FK on CLAVE (ID_TURNO ASC);
create unique index CLAVE_AREA_PK on CLAVE_AREA (ID_CLAVE_AREA ASC);
create  index RELATIONSHIP_25_FK on CLAVE_AREA (ID_CLAVE ASC);
create  index RELATIONSHIP_26_FK on CLAVE_AREA (ID_AREA ASC);
create unique index CLAVE_AREA_PREGUNTA_PK on CLAVE_AREA_PREGUNTA (ID_CLAVE_AREA_PRE ASC);
create  index RELATIONSHIP_27_FK on CLAVE_AREA_PREGUNTA (ID_CLAVE_AREA ASC);
create  index RELATIONSHIP_28_FK on CLAVE_AREA_PREGUNTA (ID_PREGUNTA ASC);
create unique index DETALLEINSCEST_PK on DETALLEINSCEST (ID_DET_INSC ASC);
create  index RELATIONSHIP_18_FK on DETALLEINSCEST (ID_EST ASC);
create  index RELATIONSHIP_45_FK on DETALLEINSCEST (ID_CARG_ACA ASC);
create unique index ENCUESTA_PK on ENCUESTA (ID_ENCUESTA ASC);
create  index RELATIONSHIP_37_FK on ENCUESTA (ID_PDG_DCN ASC);
create unique index ENCUESTADO_PK on ENCUESTADO (ID ASC);
create unique index ESCUELA_PK on ESCUELA (ID_ESCUELA ASC);
create  index RELATIONSHIP_41_FK on ESCUELA (ID_FACULTAD ASC);
create unique index ESTUDIANTE_PK on ESTUDIANTE (ID_EST ASC);
create  index RELATIONSHIP_49_FK on ESTUDIANTE (IDUSUARIO ASC);
create unique index EVALUACION_PK on EVALUACION (ID_EVALUACION ASC);
create  index RELATIONSHIP_14_FK on EVALUACION (ID_CARG_ACA ASC);
create unique index FACULTAD_PK on FACULTAD (ID_FACULTAD ASC);
create unique index GRUPO_CARGA_PK on GRUPO_CARGA (ID_GRUP_CARG ASC);
create unique index GRUPO_EMPAREJAMIENTO_PK on GRUPO_EMPAREJAMIENTO (ID_GRUPO_EMP ASC);
create  index RELATIONSHIP_38_FK on GRUPO_EMPAREJAMIENTO (ID_AREA ASC);
create unique index INTENTO_PK on INTENTO (ID_INTENTO ASC);
create  index RELATIONSHIP_23_FK on INTENTO (ID_EST ASC);
create  index RELATIONSHIP_32_FK on INTENTO (ID_CLAVE ASC);
create  index RELATIONSHIP_30_FK on INTENTO (ID ASC);
create unique index MATERIA_CICLO_PK on MATERIA_CICLO (ID_MAT_CI ASC);
create  index RELATIONSHIP_47_FK on MATERIA_CICLO (ID_CAT_MAT ASC);
create unique index OPCION_PK on OPCION (ID_OPCION ASC);
create  index RELATIONSHIP_21_FK on OPCION (ID_PREGUNTA ASC);
create unique index PDG_DCN_DOCENTE_PK on PDG_DCN_DOCENTE (ID_PDG_DCN ASC);
create  index FK_ESCUELA_PDG_DCN_DOCENTE_FK on PDG_DCN_DOCENTE (ID_ESCUELA ASC);
create  index RELATIONSHIP_50_FK on PDG_DCN_DOCENTE (IDUSUARIO ASC);
create unique index PENSUM_PK on PENSUM (ID_PENUM ASC);
create  index RELATIONSHIP_42_FK on PENSUM (ID_CARRERA ASC);
create unique index PENSUM_MATERIA_PK on PENSUM_MATERIA (ID_PENSUM_MATERIA ASC);
create  index RELATIONSHIP_43_FK on PENSUM_MATERIA (ID_PENUM ASC);
create  index RELATIONSHIP_44_FK on PENSUM_MATERIA (ID_CAT_MAT ASC);
create unique index PREGUNTA_PK on PREGUNTA (ID_PREGUNTA ASC);
create  index RELATIONSHIP_24_FK on PREGUNTA (ID_GRUPO_EMP ASC);
create unique index RESPUESTA_PK on RESPUESTA (ID_RESPUESTA ASC);
create  index RELATIONSHIP_29_FK on RESPUESTA (ID_INTENTO ASC);
create  index RELATIONSHIP_31_FK on RESPUESTA (ID_OPCION ASC);
create  index RELATIONSHIP_46_FK on RESPUESTA (ID_PREGUNTA ASC);
create unique index SESIONUSUARIO_PK on SESIONUSUARIO (IDSESION ASC);
create  index RELATIONSHIP_48_FK on SESIONUSUARIO (IDUSUARIO ASC);
create unique index TIPO_ITEM_PK on TIPO_ITEM (ID_TIPO_ITEM ASC);
create unique index TURNO_PK on TURNO (ID_TURNO ASC);
create  index RELATIONSHIP_39_FK on TURNO (ID_EVALUACION ASC);
create unique index USUARIO_PK on USUARIO (IDUSUARIO ASC);