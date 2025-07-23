package org.devquality.trukea.web.dtos.trueques.request;

public class CreateIntercambioRequest {
    private Long productoOfrecidoId;
    private Long productoDeseadoId;
    private String estado;

    public CreateIntercambioRequest() {}

    public CreateIntercambioRequest(Long productoOfrecidoId, Long productoDeseadoId, String estado) {
        this.productoOfrecidoId = productoOfrecidoId;
        this.productoDeseadoId = productoDeseadoId;
        this.estado = estado;
    }

    public boolean isValid() {
        return productoOfrecidoId != null && productoOfrecidoId > 0 &&
                productoDeseadoId != null && productoDeseadoId > 0 &&
                !productoOfrecidoId.equals(productoDeseadoId);
    }

    // Getters y setters
    public Long getProductoOfrecidoId() {
        return productoOfrecidoId;
    }

    public void setProductoOfrecidoId(Long productoOfrecidoId) {
        this.productoOfrecidoId = productoOfrecidoId;
    }

    public Long getProductoDeseadoId() {
        return productoDeseadoId;
    }

    public void setProductoDeseadoId(Long productoDeseadoId) {
        this.productoDeseadoId = productoDeseadoId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}