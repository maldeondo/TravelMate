package es.upm;

public class CatalogoViaje {
    private CatalogoActividades catalogo;
    private int[] minutosInicio;

    public CatalogoViaje(int maxActividades) {
        catalogo = new CatalogoActividades(maxActividades);

        minutosInicio = new int[maxActividades];
    }

    public CatalogoActividades getCatalogo() { return catalogo; }

    public void setInicio(int indiceActividad, int minutos) { this.minutosInicio[indiceActividad] = minutos; }
    public int getInicio(int indiceActividad) { return minutosInicio[indiceActividad]; }
}
