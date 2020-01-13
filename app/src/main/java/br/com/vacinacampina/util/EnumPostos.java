package br.com.vacinacampina.util;

import com.google.android.gms.maps.model.LatLng;

public enum  EnumPostos {


    MONTE_SANTO("Posto de Saúde Monte Santo", new LatLng(-7.208029, -35.899469)),
    PALMEIRA("Centro de Saúde Palmeira",new LatLng(-7.208647, -35.895985)),
    JEREMIAS("Posto de Saúde Jeremias",new LatLng(-7.204644, -35.900963)),
    BELA_VISTA("Posto de Saúde Bela Vista",new LatLng(-7.217121, -35.901025)),
    CUITES("Posto de Saúde Cuités",new LatLng(-7.187294, -35.904276)),
    ALTO_BRANCO("Posto de Saúde Alto Branco",new LatLng(-7.201448, -35.876592)),
    CENTRO("Centro de Saúde Dr. Francisco Pinto de Oliveira",new LatLng(-7.218724, -35.883811)),
    BODOCONGO("Posto de Saúde Bodocongó",new LatLng(-7.207083, -35.933156)),
    LIBERDADE("Posto de Saúde Liberdade",new LatLng(-7.247422, -35.900526)),
    CATOLE("Posto de Saúde Catolé",new LatLng(-7.237384, -35.884914)),
    SANTA_ROSA("Posto de Saúde Santa Rosa",new LatLng(-7.228869, -35.904483)),
    ESTACAO_VELHA("Posto de Saúde Estação Velha",new LatLng(-7.230270, -35.885190)),
    JOSE_PINHEIRO("Posto de Saúde José Pinheiro",new LatLng(-7.221043, -35.868621)),
    ACACIO_FIGUEIREDO("Posto de Saúde Acacio FIgueiredo",new LatLng(-7.269228, -35.924053)),
    RAMADINHA_1("Posto de Saúde Ramadinha I",new LatLng(-7.215277, -35.928698)),
    BODOCONGO_2("Posto de Saúde Bodocongó - João Rique",new LatLng(-7.223174, -35.917877)),
    JARDIN_TAVARES("Posto de Saúde Jardim Tavares",new LatLng(-7.196853, -35.876331)),
    JARDIN_TAVARES_2("Posto de Saúde Jardim Tavares - Severino Cabral",new LatLng(-7.196853, -35.876331)),
    CENTENARIO("Posto de Saúde Centenário",new LatLng(-7.224536, -35.910571)),
    JOSE_PINHEIRO_2("Posto de Saúde José Pinheiro II",new LatLng(-7.226536, -35.872219)),
    CATOLE_2("Posto de Saúde Catolé II ",new LatLng(-7.228605, -35.877816)),
    CATOLE_3("Posto de Saúde Catolé III ",new LatLng(-7.230819, -35.879349)),
    JARDIM_CONTINENTAL("Posto de Saúde Jardim Continental ",new LatLng(-7.195775, -35.893313)),
    ROSA_MISTICA("Posto de Saúde Rosa Mistica ",new LatLng(-7.207820, -35.886204)),
    PEDREGAL("Posto de Saúde Pedregal ",new LatLng(-7.221538, -35.908317)),
    CRUZEIRO("Posto de Saúde Cruzeiro ",new LatLng(-7.246211, -35.912599)),
    NOVA_BRASILIA("Posto de Saúde Nova Brasília ",new LatLng(-7.246211, -35.912599)),
    VILA_CABRAL("Posto de Saúde Vila Cabral ",new LatLng(-7.240372, -35.864091)),
    QUARENTA("Posto de Saúde Quarenta ",new LatLng(-7.233087, -35.902253)),
    MULTIRAO("Posto de Saúde Multirão ",new LatLng(-7.227394, -35.947655)),
    TRES_IRMAS("Posto de Saúde Três Irmãs ",new LatLng(-7.251269, -35.921230)),
    JARDIM_PAULISTANO("Posto de Saúde Jardim Paulistano ",new LatLng(-7.250031, -35.895422)),
    MONTE_CASTELO("Posto de Saúde Monte Castelo",new LatLng(-7.218392, -35.865964)),
    NACOES("Posto de Saúde Nações",new LatLng(-7.199635, -35.884484)),
    VERDEJANTE("Posto de Saúde Verdejante",new LatLng(-7.253938, -35.933657)),
    SITIO_ESTREITO("Posto de Saúde Sítio Estreito",new LatLng(-7.253938, -35.933657)),
    CINZA("Posto de Saúde Cinza",new LatLng(-7.252422, -35.926111)),
    SANTA_ROSA_2("Posto de Saúde Santa Rosa II",new LatLng(-7.232668, -35.904310));


    private String titulo;
    private LatLng localizacao;

    EnumPostos(String titulo, LatLng localizacao) {
        this.titulo = titulo;
        this.localizacao = localizacao;
    }

    public String getTitulo() {
        return titulo;
    }

    public LatLng getLocalizacao() {
        return localizacao;
    }
}
