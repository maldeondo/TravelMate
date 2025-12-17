package es.upm;

public class MatrizViaje {
    private CatalogoActividades catalogo;
    private int[] minutosInicio;

    public MatrizViaje(int maxActividades) {
        catalogo = new CatalogoActividades(maxActividades);

        minutosInicio = new int[maxActividades];
    }

    public CatalogoActividades getCatalogo() { return catalogo; }

    public void setInicio(int indiceActividad, int minutos) { this.minutosInicio[indiceActividad] = minutos; }

    
    public void insertarActividadMatrix(Actividad actividad, int indiceActividad, int inicio) {
        catalogo.insertarActividad(actividad, indiceActividad);

        insertarInicio(indiceActividad, inicio);
    }

    public void eliminarActividadMatrix(Actividad actividad, int indiceActividad) {
        catalogo.eliminarActividad(actividad);

        eliminarInicio(indiceActividad);
    }


    private void insertarInicio(int indiceActividad, int minutos) {
        for (int i = catalogo.getNumActividades() - 2; i >= indiceActividad; i--) {
            minutosInicio[i + 1] = minutosInicio[i];
        }

        minutosInicio[indiceActividad] = minutos;
    }

    private void eliminarInicio(int indiceActividad) {
        for (int i = indiceActividad; i < catalogo.getNumActividades() - 2; i++) {
            minutosInicio[i] = minutosInicio[i + 1];
        }
    }

    public int getInicio(int indiceActividad) { return minutosInicio[indiceActividad]; }
}
