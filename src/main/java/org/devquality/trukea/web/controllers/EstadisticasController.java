
/*package org.devquality.trukea.web.controllers;

//import io.javalin.http.Context;
//import io.javalin.http.HttpStatus;
//import org.devquality.trukea.services.IEstadisticasService;
//import org.devquality.trukea.web.dtos.common.ErrorResponse;
//*import org.devquality.trukea.web.dtos.usuarios.response.EstadisticasUsuarioResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

//public class EstadisticasController {
  //  private final IEstadisticasService estadisticasService;
   // private static final Logger logger = LoggerFactory.getLogger(EstadisticasController.class);

    //public EstadisticasController(IEstadisticasService estadisticasService) {
        //this.estadisticasService = estadisticasService;
    }

   // public void getEstadisticasUsuario(Context ctx) {
   //     try {
          //  String idParam = ctx.pathParam("id");
           // if (idParam == null || idParam.trim().isEmpty()) {
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(new ErrorResponse("El ID de usuario es requerido"));
                return;
            }

            Long usuarioId;
            try {
                usuarioId = Long.parseLong(idParam);
            } catch (NumberFormatException e) {
                ctx.status(HttpStatus.BAD_REQUEST)
                        .json(new ErrorResponse("El ID debe ser un número válido"));
                return;
            }

            //EstadisticasUsuarioResponse estadisticas = this.estadisticasService.getEstadisticasUsuario(usuarioId);
            //ctx.status(HttpStatus.OK).json(estadisticas);

        } //catch (IllegalArgumentException e) {
            //ctx.status(HttpStatus.BAD_REQUEST).json(new ErrorResponse(e.getMessage()));
        } //catch (Exception e) {
            //logger.error("Error al obtener estadísticas de usuario", e);
            //ctx.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   // .json(new ErrorResponse("Error interno del servidor"));
        }
    }
}
*/
