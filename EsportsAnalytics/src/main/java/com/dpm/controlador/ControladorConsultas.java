package com.dpm.controlador;

import com.dpm.repositorio.ConsultasDAO;
import com.dpm.repositorio.ConsultasDAOImpl;
import com.dpm.util.ConexionMongo;
import com.dpm.vistas.ConsultasAvanzadas;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class ControladorConsultas {

    private ConsultasAvanzadas vistaConsultas;
    private ConsultasDAO consultasDAO;

    public ControladorConsultas(ConsultasAvanzadas vistaConsultas) {
        this.vistaConsultas = vistaConsultas;
        this.consultasDAO = new ConsultasDAOImpl();


        //LISTENERS
        this.vistaConsultas.getBtEjecutarConAvanzada().addActionListener(e -> ejecutarConAvanzada());
    }

    private void ejecutarConAvanzada() {
        int consulta = vistaConsultas.getCbTiposConsultas().getSelectedIndex();
        String info = "";
        vistaConsultas.getTxaInfo().setLineWrap(true);
        vistaConsultas.getTxaInfo().setWrapStyleWord(true); // Ajusta las líneas por palabras completas

        switch (consulta) {
            case 0 -> {
                this.vistaConsultas.getTablaConsultas().setModel(consultasDAO.obtenerJugadoresConEquipo());
                info = "La consulta obtiene los jugadores junto con la información de sus equipos," +
                        " mostrando datos como nombre, posición, nacionalidad, KDA, participación en kills," +
                        " nombre del equipo y región, combinando las colecciones 'jugador' y 'equipo'";

            }
            case 1 -> {
                this.vistaConsultas.getTablaConsultas().setModel(consultasDAO.obtenerJugadoresConEquipoYTitulos());
                info = "La consulta obtiene los jugadores junto con la información de sus equipos," +
                        " mostrando datos como nombre, posición, nacionalidad, nombre del equipo, región," +
                        " y los títulos obtenidos por el equipo en sus categorías MSI, Worlds y Liga Nacional," +
                        " combinando las colecciones 'jugador' y 'equipo'";
            }
            case 2 -> {
                this.vistaConsultas.getTablaConsultas().setModel(consultasDAO.obtenerEquiposConTitulosLigaNacional());
                info = "La consulta obtiene los equipos que tienen al menos un título de \"Liga Nacional\" " +
                        "con una cantidad mayor o igual a 3, mostrando datos como el nombre del equipo, la región" +
                        " y la cantidad de títulos obtenidos en esta categoría";
            }
            case 3 ->{
                this.vistaConsultas.getTablaConsultas().setModel(consultasDAO.obtenerJugadoresConBuenasEstadisticas());
                info = "La consulta obtiene los jugadores que tienen un KDA mayor a 5 y una participación en kills" +
                        " superior al 40%, mostrando datos como el nombre del jugador, su posición, KDA y participación" +
                        " en kills";
            }
            case 4 ->{
                this.vistaConsultas.getTablaConsultas().setModel(consultasDAO.obtenerEquiposConTitulosNacionalesOrdenados());
                info = "La consulta obtiene los equipos junto con su cantidad de títulos en la categoría \"Liga Nacional\"," +
                        " mostrando el nombre del equipo y la cantidad de títulos, ordenados de mayor a menor según la" +
                        " cantidad de títulos en esta categoría";
            }
            case 5 ->{
                this.vistaConsultas.getTablaConsultas().setModel(consultasDAO.obtenerEquiposConTotalTitulos());
                info = "La consulta obtiene los equipos junto con el total de títulos que han ganado, calculando la suma" +
                        " de los valores en el campo \"cantidad\" de la lista \"titulos\", mostrando el nombre del equipo" +
                        " y el total de títulos.";
            }
            default -> info = "";
        }
        this.vistaConsultas.getTxaInfo().setText(info);
    }

    /*
    private void obtenerJugadoresConEquipo() {
        try {
            // Obtener la colección "jugador"
            MongoCollection<Document> jugadoresCollection = ConexionMongo.getCollection("jugador");

            // Definir la consulta aggregate
            List<Document> pipeline = List.of(
                    new Document("$lookup", new Document()
                            .append("from", "equipo")
                            .append("localField", "id_equipo")
                            .append("foreignField", "_id")
                            .append("as", "equipo_info")
                    ),
                    new Document("$unwind", "$equipo_info"),
                    new Document("$project", new Document()
                            .append("jugador", new Document()
                                    .append("nombre", "$nombre")
                                    .append("posicion", "$posicion")
                                    .append("nacionalidad", "$nacionalidad")
                                    .append("kda", "$estadisticas.kda")
                                    .append("participacion_kill", "$estadisticas.participacion_kill")
                            )
                            .append("equipo", new Document()
                                    .append("nombre", "$equipo_info.nombre")
                                    .append("region", "$equipo_info.region")
                            )
                    )
            );

            // Ejecutar la consulta aggregate
            MongoCursor<Document> cursor = jugadoresCollection.aggregate(pipeline).iterator();

            // Configurar el modelo de la tabla
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Nombre del Jugador");
            model.addColumn("Posición");
            model.addColumn("Nacionalidad");
            model.addColumn("KDA");
            model.addColumn("Participación en Kills");
            model.addColumn("Nombre del Equipo");
            model.addColumn("Región");

            // Recorrer y agregar los resultados al modelo
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Document jugador = (Document) doc.get("jugador");
                Document equipo = (Document) doc.get("equipo");

                model.addRow(new Object[] {
                        jugador.getString("nombre"),
                        jugador.getString("posicion"),
                        jugador.getString("nacionalidad"),
                        jugador.get("kda"),
                        jugador.get("participacion_kill"),
                        equipo.getString("nombre"),
                        equipo.getString("region")
                });
            }

            // Asignar el modelo a la tabla
            this.vistaConsultas.getTablaConsultas().setModel(model);

        } catch (Exception e) {
            System.err.println("Error ejecutando la consulta: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void obtenerJugadoresConEquipoYTitulos() {
        try {
            // Obtener la colección "jugador"
            MongoCollection<Document> jugadoresCollection = ConexionMongo.getCollection("jugador");

            // Definir la consulta aggregate
            List<Document> pipeline = List.of(
                    new Document("$lookup", new Document()
                            .append("from", "equipo")
                            .append("localField", "id_equipo")
                            .append("foreignField", "_id")
                            .append("as", "equipo_info")
                    ),
                    new Document("$unwind", "$equipo_info"),
                    new Document("$project", new Document()
                            .append("jugador", new Document()
                                    .append("nombre", "$nombre")
                                    .append("posicion", "$posicion")
                                    .append("nacionalidad", "$nacionalidad")
                            )
                            .append("equipo", new Document()
                                    .append("nombre", "$equipo_info.nombre")
                                    .append("region", "$equipo_info.region")
                                    .append("titulos", "$equipo_info.titulos")
                            )
                    )
            );

            // Ejecutar la consulta aggregate
            MongoCursor<Document> cursor = jugadoresCollection.aggregate(pipeline).iterator();

            // Configurar el modelo de la tabla
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Nombre del Jugador");
            model.addColumn("Posición");
            model.addColumn("Nacionalidad");
            model.addColumn("Nombre del Equipo");
            model.addColumn("Región");
            model.addColumn("MSI");
            model.addColumn("Worlds");
            model.addColumn("Liga Nacional");

            // Recorrer y agregar los resultados al modelo
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Document jugador = (Document) doc.get("jugador");
                Document equipo = (Document) doc.get("equipo");
                List<Document> titulos = equipo.getList("titulos", Document.class);

                // Inicializar las variables para los títulos
                Integer msi = 0, worlds = 0, ligaNacional = 0;

                // Buscar los títulos
                for (Document titulo : titulos) {
                    String tipo = titulo.getString("tipo");
                    int cantidad = titulo.getInteger("cantidad");

                    if ("MSI".equals(tipo)) {
                        msi = cantidad;
                    } else if ("Worlds".equals(tipo)) {
                        worlds = cantidad;
                    } else if ("Liga Nacional".equals(tipo)) {
                        ligaNacional = cantidad;
                    }
                }

                // Agregar la fila con los datos
                model.addRow(new Object[]{
                        jugador.getString("nombre"),  // Nombre del jugador
                        jugador.getString("posicion"), // Posición
                        jugador.getString("nacionalidad"), // Nacionalidad
                        equipo.getString("nombre"),    // Nombre del equipo
                        equipo.getString("region"),    // Región
                        msi,                           // MSI
                        worlds,                        // Worlds
                        ligaNacional                   // Liga Nacional
                });
            }

            // Asignar el modelo a la tabla
            this.vistaConsultas.getTablaConsultas().setModel(model);

        } catch (Exception e) {
            System.err.println("Error ejecutando la consulta: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void obtenerEquiposConTitulosLigaNacional() {
        try {
            // Obtener la colección "equipo"
            MongoCollection<Document> equipoCollection = ConexionMongo.getCollection("equipo");

            // Definir la consulta find
            Bson filter = Filters.elemMatch("titulos", Filters.and(
                    Filters.eq("tipo", "Liga Nacional"),
                    Filters.gte("cantidad", 3)
            ));

            // Ejecutar la consulta find
            MongoCursor<Document> cursor = equipoCollection.find(filter).iterator();

            // Configurar el modelo de la tabla
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Nombre del Equipo");
            model.addColumn("Región");
            model.addColumn("Títulos de Liga Nacional");

            // Recorrer y agregar los resultados al modelo
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                String equipoNombre = doc.getString("nombre");
                String region = doc.getString("region");
                List<Document> titulos = (List<Document>) doc.get("titulos");

                // Buscar los títulos de Liga Nacional
                int ligaNacionalCantidad = 0;
                for (Document titulo : titulos) {
                    if ("Liga Nacional".equals(titulo.getString("tipo"))) {
                        ligaNacionalCantidad = titulo.getInteger("cantidad");
                    }
                }

                // Agregar la fila con los datos
                model.addRow(new Object[]{
                        equipoNombre,
                        region,
                        ligaNacionalCantidad
                });
            }

            // Asignar el modelo a la tabla
            this.vistaConsultas.getTablaConsultas().setModel(model);

        } catch (Exception e) {
            System.err.println("Error ejecutando la consulta: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void obtenerJugadoresConBuenasEstadisticas() {
        try {
            // Obtener la colección "jugador"
            MongoCollection<Document> jugadorCollection = ConexionMongo.getCollection("jugador");

            // Definir la consulta find
            Bson filter = Filters.and(
                    Filters.gt("estadisticas.kda", 5),
                    Filters.gt("estadisticas.participacion_kill", 40)
            );

            // Ejecutar la consulta find
            MongoCursor<Document> cursor = jugadorCollection.find(filter).iterator();

            // Configurar el modelo de la tabla
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Nombre del Jugador");
            model.addColumn("Posición");
            model.addColumn("KDA");
            model.addColumn("Participación en Kills");

            // Recorrer y agregar los resultados al modelo
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Document estadisticas = (Document) doc.get("estadisticas");

                // Agregar la fila con los datos
                model.addRow(new Object[]{
                        doc.getString("nombre"),
                        doc.getString("posicion"),
                        estadisticas.getDouble("kda"),
                        estadisticas.getInteger("participacion_kill")
                });
            }

            // Asignar el modelo a la tabla
            this.vistaConsultas.getTablaConsultas().setModel(model);

        } catch (Exception e) {
            System.err.println("Error ejecutando la consulta: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void obtenerEquiposConTitulosNacionalesOrdenados() {
        try {
            // Obtener la colección "equipo"
            MongoCollection<Document> equipoCollection = ConexionMongo.getCollection("equipo");

            // Definir la consulta aggregate
            List<Document> pipeline = List.of(
                    new Document("$project", new Document()
                            .append("nombre", 1)
                            .append("titulos_nacionales", new Document("$arrayElemAt", List.of(
                                    new Document("$filter", new Document()
                                            .append("input", "$titulos")
                                            .append("as", "titulo")
                                            .append("cond", new Document("$eq", List.of("$$titulo.tipo", "Liga Nacional")))
                                    ),
                                    0  // Obtener el primer elemento de la lista filtrada
                            )))
                    ),
                    new Document("$sort", new Document("titulos_nacionales.cantidad", -1))  // Ordenar por cantidad de títulos nacionales de mayor a menor
            );

            // Ejecutar la consulta aggregate
            MongoCursor<Document> cursor = equipoCollection.aggregate(pipeline).iterator();

            // Configurar el modelo de la tabla
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Nombre del Equipo");
            model.addColumn("Títulos Nacionales");

            // Recorrer y agregar los resultados al modelo
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                String nombreEquipo = doc.getString("nombre");
                Document titulosNacionales = (Document) doc.get("titulos_nacionales");

                int cantidadTitulos = 0;
                if (titulosNacionales != null) {
                    cantidadTitulos = titulosNacionales.getInteger("cantidad", 0);  // Si no tiene cantidad, por defecto será 0
                }

                // Agregar la fila con los datos
                model.addRow(new Object[]{
                        nombreEquipo,
                        cantidadTitulos
                });
            }

            // Asignar el modelo a la tabla
            this.vistaConsultas.getTablaConsultas().setModel(model);

        } catch (Exception e) {
            System.err.println("Error ejecutando la consulta: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void obtenerEquiposConTotalTitulos() {
        try {
            // Obtener la colección "equipo"
            MongoCollection<Document> equiposCollection = ConexionMongo.getCollection("equipo");

            // Definir la consulta aggregate
            List<Document> pipeline = List.of(
                    new Document("$addFields", new Document("totalTitulos", new Document("$sum", new Document("$map", new Document()
                            .append("input", "$titulos")
                            .append("as", "titulo")
                            .append("in", "$$titulo.cantidad")
                    )))),
                    new Document("$project", new Document("nombre", 1).append("totalTitulos", 1))
            );

            // Ejecutar la consulta aggregate
            MongoCursor<Document> cursor = equiposCollection.aggregate(pipeline).iterator();

            // Configurar el modelo de la tabla
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Nombre del Equipo");
            model.addColumn("Total de Títulos");

            // Recorrer y agregar los resultados al modelo
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                model.addRow(new Object[]{
                        doc.getString("nombre"),
                        doc.getInteger("totalTitulos")
                });
            }

            // Asignar el modelo a la tabla
            this.vistaConsultas.getTablaConsultas().setModel(model);

        } catch (Exception e) {
            System.err.println("Error ejecutando la consulta: " + e.getMessage());
            e.printStackTrace();
        }
    }
     */
}
