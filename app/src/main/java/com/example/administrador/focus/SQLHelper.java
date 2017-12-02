package com.example.administrador.focus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLHelper extends SQLiteOpenHelper {

	Context ctx;
	public SQLHelper(Context context) {
		super(context, "focusdb", null, 1);
		// TODO Auto-generated constructor stub
		ctx=context;
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		arg0.execSQL("CREATE TABLE usuarios (" +
				"  nombre_usu varchar(20) NOT NULL," +
				"  clave varchar(10) NOT NULL," +
				"  en_linea char(1) NOT NULL DEFAULT '0'," +
				"  PRIMARY KEY (`nombre_usu`))");

		arg0.execSQL("CREATE TABLE `caracteristicas` (\n" +
				"  `nombre_usu_usuarios` varchar(20) NOT NULL,\n" +
				"  `estatura` decimal(3,0) NOT NULL,\n" +
				"  `peso` decimal(3,0) NOT NULL,\n" +
				"  `imc` decimal(3,0) NOT NULL,\n" +
				"  `id_somatotipo_somatotipos` char(1) NOT NULL," +
				"	PRIMARY KEY (`nombre_usu_usuarios`)," +
				"	UNIQUE (`id_somatotipo_somatotipos`)," +
				"	UNIQUE (`nombre_usu_usuarios`)," +
				"	FOREIGN KEY (`nombre_usu_usuarios`) REFERENCES `usuarios` (`nombre_usu`)," +
				"	FOREIGN KEY (`id_somatotipo_somatotipos`) REFERENCES `somatotipos` (`id_somatotipo`))");

		arg0.execSQL("CREATE TABLE `descansos` (\n" +
				"  `id_somatotipo_somatotipos` char(1) NOT NULL,\n" +
				"  `tiempo` text NOT NULL,\n" +
				"	UNIQUE (`id_somatotipo_somatotipos`)," +
				"	FOREIGN KEY (`id_somatotipo_somatotipos`) REFERENCES `somatotipos` (`id_somatotipo`))");

		arg0.execSQL("CREATE TABLE `dietas` (\n" +
				"  `id_somatotipo_somatotipos` char(1) NOT NULL,\n" +
				"  `carbohidratos` char(3) NOT NULL,\n" +
				"  `proteinas` char(3) NOT NULL,\n" +
				"  `grasas` char(3) NOT NULL," +
				"	UNIQUE (`id_somatotipo_somatotipos`)," +
				"	FOREIGN KEY (`id_somatotipo_somatotipos`) REFERENCES `somatotipos` (`id_somatotipo`))");

		arg0.execSQL("CREATE TABLE `ejercicios` (\n" +
				"  `id_ejercicio` int(2) NOT NULL,\n" +
				"  `nombre` varchar(20) NOT NULL,\n" +
				"  `parte_cuerpo` varchar(10) NOT NULL," +
                "  `animacion` varchar(10) DEFAULT NULL," +
				"	PRIMARY KEY (`id_ejercicio`))");

		arg0.execSQL("CREATE TABLE `intensidad_ejercicios` (\n" +
				"  `id_somatotipo_somatotipos` char(1) NOT NULL,\n" +
				"  `intensidad` text NOT NULL," +
				"	UNIQUE (`id_somatotipo_somatotipos`)," +
				"	FOREIGN KEY (`id_somatotipo_somatotipos`) REFERENCES `somatotipos` (`id_somatotipo`))");

		arg0.execSQL("CREATE TABLE `somatotipos` (\n" +
				"  `id_somatotipo` char(1) NOT NULL,\n" +
				"  `nombre` varchar(10) NOT NULL," +
				"	PRIMARY KEY (`id_somatotipo`))");

		arg0.execSQL("CREATE TABLE `tiempo_semanal` (\n" +
				"  `id_somatotipo_somatotipos` char(1) NOT NULL,\n" +
				"  `horas_semana` varchar(5) NOT NULL,\n" +
				"  `dias_semana` char(1) NOT NULL,\n" +
				"  `division_tiempo` text NOT NULL," +
				"	UNIQUE (`id_somatotipo_somatotipos`)," +
				"	FOREIGN KEY (`id_somatotipo_somatotipos`) REFERENCES `somatotipos` (`id_somatotipo`))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	}
	
	SQLHelper helper;
	SQLiteDatabase db;

	public void opendb(){
		helper=new SQLHelper(ctx);
		db=helper.getWritableDatabase();
	}
	
	public void closedb(){
		db.close();
	}

	public void launchData(){
		try {
			db.execSQL("INSERT INTO `somatotipos` (`id_somatotipo`, `nombre`) VALUES\n" +
					"('1', 'Ectomorfo'),\n" +
					"('2', 'Mesomorfo'),\n" +
					"('3', 'Endomorfo');\n");
		}catch (Exception e){
			e.printStackTrace();
		}

		try {
			db.execSQL("INSERT INTO `descansos` (`id_somatotipo_somatotipos`, `tiempo`) VALUES\n" +
					"('1', '90-120 segundos entre series'),\n" +
					"('2', '60-90 segundos entre series'),\n" +
					"('3', '30-60 segundos entre series');\n");
		}catch (Exception e){
			e.printStackTrace();
		}

		try {
			db.execSQL("INSERT INTO `dietas` (`id_somatotipo_somatotipos`, `carbohidratos`, `proteinas`, `grasas`) VALUES\n" +
					"('1', '55%', '25%', '20%'),\n" +
					"('2', '40%', '30%', '30%'),\n" +
					"('3', '25%', '35%', '40%');\n");
		}catch (Exception e){
			e.printStackTrace();
		}

		try {
			db.execSQL("INSERT INTO `intensidad_ejercicios` (`id_somatotipo_somatotipos`, `intensidad`) VALUES\n" +
					"('1', 'Poco peso:4-5 Series:10-15 Repeticiones'),\n" +
					"('2', 'Peso moderado:3-4 Series:8-12 Repeticiones'),\n" +
					"('3', 'Bastante peso:3-2 Series:5-8 Repeticiones');\n");
		}catch (Exception e){
			e.printStackTrace();
		}

		try {
			db.execSQL("INSERT INTO `tiempo_semanal` (`id_somatotipo_somatotipos`, `horas_semana`, `dias_semana`, `division_tiempo`) VALUES\n" +
					"('1', '4 hor', '4', '2 dÃ\u00ADas de trabajo 1 de descanso - 2 dÃ\u00ADas de trabajo 2 de descanso'),\n" +
					"('2', '5-6 h', '5', '3 dÃ\u00ADas de trabajo por 1 de descanso'),\n" +
					"('3', '7-8 h', '6', '6 dÃ\u00ADas de trabajo por 1 de descanso');\n");
		}catch (Exception e){
			e.printStackTrace();
		}

		try {
			db.execSQL("INSERT INTO `ejercicios` (`id_ejercicio`, `nombre`, `parte_cuerpo`, `animacion`) VALUES\n" +
					"(1, ' Elevación de piernas', 'ABDOMEN','animacion1'),\n" +
					"(2, 'Inclinaciones laterales con mancuerna', 'ABDOMEN','animacion2'),\n" +
					"(3, 'Flexión de piernas en banca ', 'ABDOMEN','animacion3'),\n" +
					"(4, 'Curl abdominal', 'ABDOMEN','animacion4'),\n" +

					"(5, 'Curl de Antebrazos', 'ANTEBRAZO','animacion5'),\n" +
					"(6, 'Curl de Antebrazos con Agarre Invertido', 'ANTEBRAZO','animacion6'),\n" +
					"(7, 'Curl con Barra Invertido(para Bisceps y Antebrazos)', 'ANTEBRAZO','animacion7'),\n" +

					"(9, 'Predicador con barra ', 'BICEPS','animacion8'),\n" +
					"(10, 'Curl concentración con mancuerna', 'BICEPS','animacion9'),\n" +
					"(11, 'Curl en martillo', 'BICEPS','animacion10'),\n" +
					"(12, 'Curl con barra', 'BICEPS','animacion11'),\n" +
					"(13, 'Predicador con mancuerna', 'BICEPS','animacion12'),\n" +

					"(14, 'dominadas', 'ESPALDA','animacion13'),\n" +
					"(15, 'Extensiones de espalda', 'ESPALDA','animacion14'),\n" +
					"(16, 'jalones laterales', 'ESPALADA','animacion15'),\n" +
					"(17, 'peso muerto', 'ESPALDA','animacion16'),\n" +
					"(18, 'remadas sentado', 'ESPALDA','animacion17'),\n" +

					"(19, 'Elevaciones del deltoide posterior con mancuernas', 'HOMBROS','animacion18'),\n" +
					"(20, 'Elevaciones frontales', 'HOMBROS','animacion19'),\n" +
					"(21, 'Elevaciones laterales con polea baja', 'HOMBROS','animacion20'),\n" +
					"(22, 'Elevaciones laterales', 'HOMBROS','animacion21'),\n" +
					"(23, 'elevacion de gemelos con pesa sentado', 'PANTORRILLA','animacion22'),\n" +
					"(24, 'Elevacion de gemelos con pesa una sola pierna sentado', 'PANTORRILLA','animacion23'),\n" +
					"(25, 'elevacion pantorillas sentado', 'PANTORIILLA','animacion24'),\n" +
					"(26, 'elevaciones dedos del pie con una sola pierna', 'PANTORRILLA','animacion25'),\n" +
					"(27, 'elevaciones dedos del pie', 'PANTORRILLA','animacion26'),\n" +
					"(33, 'Crucifijo con mancuernas', 'PECHO','animacion27'),\n" +
					"(34, 'Crucifijo cruzado con cable', 'PECHO','animacion28'),\n" +
					"(35, 'Dips en barras paralelas', 'PECHO','animacion29'),\n" +
					"(36, 'Extensiones de pierna', 'PIERNA','animacion30'),\n" +
					"(37, 'Lunges con mancuerna', 'PIERNA','animacion2'),\n" +
					"(38, 'Patada hacia atras con cable', 'PIERNA','animacion2'),\n" +
					"(39, 'Press de pierna inclinado', 'PIERNA','animacion2'),\n" +
					"(40, 'Sentadillas con mancuerna', 'PIERNA','animacion2'),\n" +
					"(41, 'Sentadillas piernas', 'PIERNA','animacion2'),\n" +
					"(42, 'Estensiones de triceps de un brazo con mancuernas', 'TRICEPS','animacion2'),\n" +
					"(43, 'Extensiones de triceps con barra', 'TRICEPS','animacion2'),\n" +
					"(44, 'Extensiones de triceps con mancuernas', 'TRICEPS','animacion2'),\n" +
					"(45, 'Press de banca con agarre cerrado', 'TRICEPS','animacion2'" +
                    ");");
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public long insert_user(ContentValues values)throws Exception {
		return db.insert("usuarios", null, values);
	}
	
	public boolean exist_user(String user){
		Cursor result=db.rawQuery("select nombre_usu from usuarios where nombre_usu like'"+user+"'", null);
		if(result.getCount()>0) return true;
		return false;	
	}
	
	public boolean validate_user (String user, String password)throws Exception {
		Cursor result=db.rawQuery("select * from usuarios where nombre_usu like'"+user+"' and clave like'"+password+"'", null);
		if(result.getCount()>0)return true;
		return false;
	}

	public Boolean user_initialize(String user){
		Cursor result=db.rawQuery("select * from caracteristicas where nombre_usu_usuarios like '"+user+"'", null);
		if(result.getCount()>0)return true;
		return false;
	}

	public long initialize_user(String user, String weidth, String height, String BMI, String somatotype){
		ContentValues values = new ContentValues();
		values.put("nombre_usu_usuarios",user);
		values.put("estatura", height);
		values.put("peso",weidth);
		values.put("imc",BMI);
		values.put("id_somatotipo_somatotipos",somatotype);
		return db.insert("caracteristicas",null,values);
	}

	public long update_user(String user,String weidth, String height, String BMI, String somatotype){
		ContentValues values = new ContentValues();
		values.put("estatura", height);
		values.put("peso",weidth);
		values.put("imc",BMI);
		values.put("id_somatotipo_somatotipos",somatotype);
		return db.update("caracteristicas",values,"nombre_usu_usuarios='"+user+"'",null);
	}

	public int setSession(Boolean state, String user){
		ContentValues values = new ContentValues();
		if(state){
			values.put("en_linea",1);
			return db.update("usuarios",values,"nombre_usu='"+user+"'",null);
		}else{
			values.put("en_linea",0);
			return db.update("usuarios",values,"nombre_usu='"+user+"'",null);
		}
	}

	public Cursor checkOpenSession(){
		Cursor result=db.rawQuery("select * from usuarios where en_linea == '1'", null);
		return result;
	}

	public Cursor consult_user(String user){
		Cursor result=db.rawQuery("select * from caracteristicas where nombre_usu_usuarios='"+user+"'", null);
		return result;
	}

	public  Cursor consultRecommendationDiet(String user){
		String somatotipo;
		Cursor result=db.rawQuery("select * from caracteristicas where nombre_usu_usuarios='"+user+"'", null);
		if(result.moveToFirst()){
			somatotipo = result.getString(result.getColumnIndex("id_somatotipo_somatotipos"));
			result=db.rawQuery("select * from dietas where id_somatotipo_somatotipos='"+somatotipo+"'", null);
			Log.i("sql", String.valueOf(result.getCount()));
			return result;
		}
		return result=db.rawQuery(null, null);
	}

	public  Cursor consultRecommendationRest(String user){
		String somatotipo;
		Cursor result=db.rawQuery("select * from caracteristicas where nombre_usu_usuarios='"+user+"'", null);
		if(result.moveToFirst()){
			somatotipo = result.getString(result.getColumnIndex("id_somatotipo_somatotipos"));
			result=db.rawQuery("select * from descansos where id_somatotipo_somatotipos='"+somatotipo+"'", null);
			Log.i("sql", String.valueOf(result.getCount()));
			return result;
		}
		return result=db.rawQuery(null, null);
	}

	public  Cursor consultRecommendationIntensity(String user){
		String somatotipo;
		Cursor result=db.rawQuery("select * from caracteristicas where nombre_usu_usuarios='"+user+"'", null);
		if(result.moveToFirst()){
			somatotipo = result.getString(result.getColumnIndex("id_somatotipo_somatotipos"));
			result=db.rawQuery("select * from intensidad_ejercicios where id_somatotipo_somatotipos='"+somatotipo+"'", null);
			Log.i("sql", String.valueOf(result.getCount()));
			return result;
		}
		return result=db.rawQuery(null, null);
	}

	public  Cursor consultRecommendationTime(String user){
		String somatotipo;
		Cursor result=db.rawQuery("select * from caracteristicas where nombre_usu_usuarios='"+user+"'", null);
		if(result.moveToFirst()){
			somatotipo = result.getString(result.getColumnIndex("id_somatotipo_somatotipos"));
			result=db.rawQuery("select * from tiempo_semanal where id_somatotipo_somatotipos='"+somatotipo+"'", null);
			Log.i("sql", String.valueOf(result.getCount()));
			return result;
		}
		return result=db.rawQuery(null, null);
	}

	public Cursor consultExercise(String part){
		Cursor result=db.rawQuery("select * from ejercicios where parte_cuerpo='"+part+"'", null);
		return result;
	}

	//----------------------------------------------------------------------------------------

	public Cursor openSession2(){
		Cursor result=db.rawQuery("select * from caracteristicas", null);
		return result;
	}

	public Boolean borrar(){
		db.delete("dietas",null,null);
		return true;
	}

	public  void  cerrar(){
		ContentValues values = new ContentValues();
		values.put("en_linea",0);
		db.update("usuarios",values,"nombre_usu='mario'",null);
	}
}
