$(document).ready(() => {
	$("#producto").autocomplete({
		source: (request, response) => {
			$.ajax({
				url: "/compra/cargar-productos/" + request.term,
				dataType: "json",
				data: {
					term: request.term
				},
				success: (data) => {
					response($.map(data, (element) => {
						return {
							value: element.id,
							label: element.nombre,
							precio: element.precioCompra,
						};
					}));
				},
			});
		},
		select: (event, ui) => {
			
			if(existeProducto(ui.item.value)){
				incrementarCantidad(ui.item.value, ui.item.precio);
				
				return false;
			}
			
			let fila = $("#productosPlantilla").html();
			
			fila = fila.replace(/{ID}/g, ui.item.value);
			fila = fila.replace(/{NOMBRE}/g, ui.item.label);
			fila = fila.replace(/{PRECIO}/g, ui.item.precio);
			
			$("#productosSeleccionados tbody").append(fila);
			
			calcularSubtotal(ui.item.value, ui.item.precio, 1)
			
			
			return false;
		}
	});
	
	$("form").submit(function(){
		$("#productosPlantilla").remove();
		return;
	});
});

function calcularSubtotal(id, precio, cantidad){
	$("#subtotal_" + id).html("S/ " + (parseFloat(precio) * parseInt(cantidad)));
	calcularTotal();
}

function existeProducto(id) {
	let existe = false;
	$("input[name='producto_id[]']").each(function() {
		if(id == $(this).val()){
			existe = true;
		}
	});
	return existe;
}

function incrementarCantidad(id, precio){
	let cantidad = $("#cantidad_" + id).val();
	$("#cantidad_" + id).val(++cantidad);
	calcularSubtotal(id, precio, cantidad);
}

function eliminarProducto(id){
	$("#fila_" + id).remove();
	calcularTotal();	
}

function calcularTotal(){
	let total = 0.0;
	$("td[id^='subtotal_']").each(function() {
		total += parseFloat($(this).html().slice(3));
	});
	$("#total").html("S/ " + total);
}
