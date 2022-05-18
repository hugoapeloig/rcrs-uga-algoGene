package convertisseur_map_to_graph.maps;

/**
   A no-op coordinate conversion.
*/
public class IdentityConversion implements CoordinateConversion {
    @Override
    public double convertX(double x) {
        return x;
    }

    @Override
    public double convertY(double y) {
        return y;
    }
}
