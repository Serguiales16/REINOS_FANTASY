package controladores;

public class Jugador {
	private int id;
	private String nombre;
	private String raza;
	private int salud;
	private int fuerza;
	private int mana;
	private float poderTotal;
	private double precio;
	private int equipoId;
	private String equipoNombre;


	public Jugador(String nombre, String raza, int salud, int fuerza, int mana, float poderTotal, double precio, String equipoNombre) {
		this.nombre = (nombre != null && !nombre.trim().isEmpty()) ? nombre : "Unknown";
		this.raza = (raza != null && !raza.trim().isEmpty()) ? raza : "Unknown";
		this.salud = Math.max(salud, 0);
		this.fuerza = Math.max(fuerza, 0);
		this.mana = Math.max(mana, 0);
		this.poderTotal = Math.max(poderTotal, 0);
		this.precio = Math.max(precio, 0);
		this.equipoNombre = (equipoNombre != null && !equipoNombre.trim().isEmpty()) ? equipoNombre : "Unknown";
	}


	public Jugador(int id, String nombre, String raza, int salud, int fuerza, int mana, float poderTotal, double precio, int equipoId, String equipoNombre) {
		this.id = id;
		this.nombre = nombre;
		this.raza = raza;
		this.salud = salud;
		this.fuerza = fuerza;
		this.mana = mana;
		this.poderTotal = poderTotal;
		this.precio = precio;
		this.equipoId = equipoId;
		this.equipoNombre = equipoNombre;
	}

	public Jugador(String nombre, String equipo, int puntuacion) {
	}

	public Jugador(int id, String nombre, String equipo, int puntuacion) {
	}


	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	public String getNombre() { return nombre; }
	public void setNombre(String nombre) { this.nombre = nombre; }
	public String getRaza() { return raza; }
	public void setRaza(String raza) { this.raza = raza; }
	public int getSalud() { return salud; }
	public void setSalud(int salud) { this.salud = salud; }
	public int getFuerza() { return fuerza; }
	public void setFuerza(int fuerza) { this.fuerza = fuerza; }
	public int getMana() { return mana; }
	public void setMana(int mana) { this.mana = mana; }
	public float getPoderTotal() { return poderTotal; }
	public void setPoderTotal(float poderTotal) { this.poderTotal = poderTotal; }
	public double getPrecio() { return precio; }
	public void setPrecio(double precio) { this.precio = precio; }
	public int getEquipoId() { return equipoId; }
	public void setEquipoId(int equipoId) { this.equipoId = equipoId; }
	public String getEquipoNombre() { return equipoNombre; }
	public void setEquipoNombre(String equipoNombre) { this.equipoNombre = equipoNombre; }

	@Override
	public String toString() {
		return "Jugador{id=" + id + ", nombre='" + nombre + "', raza='" + raza + "', salud=" + salud +
				", fuerza=" + fuerza + ", mana=" + mana + ", poderTotal=" + poderTotal +
				", precio=" + precio + ", equipo='" + equipoNombre + "'}";
	}
}