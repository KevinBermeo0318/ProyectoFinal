package persistencia;

import modelo.*;
import java.io.Serializable;
import java.util.List;

public class EstadoPartida implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Heroe> heroes;
    private int turnoActual;
    private String ubicacion;
    private boolean batallaEnCurso;
    private String nombrePartida;
    private java.util.Date fechaGuardado;

    public EstadoPartida(String nombrePartida, List<Heroe> heroes) {
        this.nombrePartida = nombrePartida;
        this.heroes = heroes;
        this.turnoActual = 1;
        this.ubicacion = "Inicio";
        this.batallaEnCurso = false;
        this.fechaGuardado = new java.util.Date();
    }

    // Getters y setters
    public List<Heroe> getHeroes() { return heroes; }
    public void setHeroes(List<Heroe> heroes) { this.heroes = heroes; }

    public int getTurnoActual() { return turnoActual; }
    public void setTurnoActual(int turnoActual) { this.turnoActual = turnoActual; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public boolean isBatallaEnCurso() { return batallaEnCurso; }
    public void setBatallaEnCurso(boolean batallaEnCurso) { this.batallaEnCurso = batallaEnCurso; }

    public String getNombrePartida() { return nombrePartida; }
    public java.util.Date getFechaGuardado() { return fechaGuardado; }
    public void actualizarFecha() { this.fechaGuardado = new java.util.Date(); }
}