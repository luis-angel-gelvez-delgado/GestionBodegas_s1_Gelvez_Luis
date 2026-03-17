const BASE_URL = 'http://localhost:8080';

function login() {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    fetch(`${BASE_URL}/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password })
    })
    .then(res => {
        if (!res.ok) throw new Error('Credenciales invalidas');
        return res.json();
    })
    .then(data => {
        localStorage.setItem('token', data.token);
        window.location.href = 'dashboard.html';
    })
    .catch(err => {
        document.getElementById('error-msg').textContent = err.message;
    });
}

function logout() {
    localStorage.removeItem('token');
    window.location.href = 'index.html';
}

function apiFetch(url, method = 'GET', body = null) {
    const token = localStorage.getItem('token');
    const options = {
        method,
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    };
    if (body) options.body = JSON.stringify(body);
    return fetch(`${BASE_URL}${url}`, options).then(res => {
        if (res.status === 403) { logout(); throw new Error('Sesion expirada'); }
        if (res.status === 204) return null;
        return res.json();
    });
}

function mostrarSeccion(nombre) {
    document.querySelectorAll('.seccion').forEach(s => s.style.display = 'none');
    document.getElementById(`seccion-${nombre}`).style.display = 'block';
    if (nombre === 'bodegas') cargarBodegas();
    if (nombre === 'productos') cargarProductos();
    if (nombre === 'movimientos') cargarMovimientos();
    if (nombre === 'reporte') cargarReporte();
    if (nombre === 'auditorias') cargarAuditorias();
}

// ==================== BODEGAS ====================

function cargarBodegas() {
    apiFetch('/api/bodegas').then(data => {
        const html = `
        <div class="form-card">
            <h3>Nueva Bodega</h3>
            <input id="b-nombre" placeholder="Nombre" />
            <input id="b-ubicacion" placeholder="Ubicacion" />
            <input id="b-capacidad" placeholder="Capacidad" type="number" />
            <input id="b-encargado" placeholder="Encargado" />
            <button onclick="crearBodega()">Crear Bodega</button>
        </div>
        <div class="card-grid">
            ${data.map(b => `
                <div class="card">
                    <h3>${b.nombre}</h3>
                    <p>Ubicacion: ${b.ubicacion}</p>
                    <p>Encargado: ${b.encargado}</p>
                    <p>Capacidad: ${b.capacidad}</p>
                    <div class="card-actions">
                        <button class="btn-edit" onclick="editarBodega(${b.id}, '${b.nombre}', '${b.ubicacion}', ${b.capacidad}, '${b.encargado}')">Editar</button>
                        <button class="btn-delete" onclick="eliminarBodega(${b.id})">Eliminar</button>
                    </div>
                </div>
            `).join('')}
        </div>`;
        document.getElementById('lista-bodegas').innerHTML = html;
    });
}

function crearBodega() {
    const body = {
        nombre: document.getElementById('b-nombre').value,
        ubicacion: document.getElementById('b-ubicacion').value,
        capacidad: parseInt(document.getElementById('b-capacidad').value),
        encargado: document.getElementById('b-encargado').value
    };
    apiFetch('/api/bodegas', 'POST', body).then(() => cargarBodegas());
}

function editarBodega(id, nombre, ubicacion, capacidad, encargado) {
    document.getElementById('lista-bodegas').innerHTML = `
        <div class="form-card">
            <h3>Editar Bodega #${id}</h3>
            <input id="eb-nombre" value="${nombre}" placeholder="Nombre" />
            <input id="eb-ubicacion" value="${ubicacion}" placeholder="Ubicacion" />
            <input id="eb-capacidad" value="${capacidad}" placeholder="Capacidad" type="number" />
            <input id="eb-encargado" value="${encargado}" placeholder="Encargado" />
            <div class="form-actions">
                <button onclick="actualizarBodega(${id})">Guardar</button>
                <button class="btn-cancel" onclick="cargarBodegas()">Cancelar</button>
            </div>
        </div>`;
}

function actualizarBodega(id) {
    const body = {
        nombre: document.getElementById('eb-nombre').value,
        ubicacion: document.getElementById('eb-ubicacion').value,
        capacidad: parseInt(document.getElementById('eb-capacidad').value),
        encargado: document.getElementById('eb-encargado').value
    };
    apiFetch(`/api/bodegas/${id}`, 'PUT', body).then(() => cargarBodegas());
}

function eliminarBodega(id) {
    if (confirm('¿Seguro que deseas eliminar esta bodega?')) {
        apiFetch(`/api/bodegas/${id}`, 'DELETE').then(() => cargarBodegas());
    }
}

// ==================== PRODUCTOS ====================

function cargarProductos() {
    apiFetch('/api/productos').then(data => {
        const html = `
        <div class="form-card">
            <h3>Nuevo Producto</h3>
            <input id="p-nombre" placeholder="Nombre" />
            <input id="p-categoria" placeholder="Categoria" />
            <input id="p-stock" placeholder="Stock" type="number" />
            <input id="p-precio" placeholder="Precio" type="number" step="0.01" />
            <button onclick="crearProducto()">Crear Producto</button>
        </div>
        <div class="card-grid">
            ${data.map(p => `
                <div class="card">
                    <h3>${p.nombre}</h3>
                    <p>Categoria: ${p.categoria}</p>
                    <p>Precio: $${p.precio}</p>
                    <p>Stock: ${p.stock}</p>
                    <div class="card-actions">
                        <button class="btn-edit" onclick="editarProducto(${p.id}, '${p.nombre}', '${p.categoria}', ${p.stock}, ${p.precio})">Editar</button>
                        <button class="btn-delete" onclick="eliminarProducto(${p.id})">Eliminar</button>
                    </div>
                </div>
            `).join('')}
        </div>`;
        document.getElementById('lista-productos').innerHTML = html;
    });
}

function crearProducto() {
    const body = {
        nombre: document.getElementById('p-nombre').value,
        categoria: document.getElementById('p-categoria').value,
        stock: parseInt(document.getElementById('p-stock').value),
        precio: parseFloat(document.getElementById('p-precio').value)
    };
    apiFetch('/api/productos', 'POST', body).then(() => cargarProductos());
}

function editarProducto(id, nombre, categoria, stock, precio) {
    document.getElementById('lista-productos').innerHTML = `
        <div class="form-card">
            <h3>Editar Producto #${id}</h3>
            <input id="ep-nombre" value="${nombre}" placeholder="Nombre" />
            <input id="ep-categoria" value="${categoria}" placeholder="Categoria" />
            <input id="ep-stock" value="${stock}" placeholder="Stock" type="number" />
            <input id="ep-precio" value="${precio}" placeholder="Precio" type="number" step="0.01" />
            <div class="form-actions">
                <button onclick="actualizarProducto(${id})">Guardar</button>
                <button class="btn-cancel" onclick="cargarProductos()">Cancelar</button>
            </div>
        </div>`;
}

function actualizarProducto(id) {
    const body = {
        nombre: document.getElementById('ep-nombre').value,
        categoria: document.getElementById('ep-categoria').value,
        stock: parseInt(document.getElementById('ep-stock').value),
        precio: parseFloat(document.getElementById('ep-precio').value)
    };
    apiFetch(`/api/productos/${id}`, 'PUT', body).then(() => cargarProductos());
}

function eliminarProducto(id) {
    if (confirm('¿Seguro que deseas eliminar este producto?')) {
        apiFetch(`/api/productos/${id}`, 'DELETE').then(() => cargarProductos());
    }
}

// ==================== MOVIMIENTOS ====================

function cargarMovimientos() {
    Promise.all([
        apiFetch('/api/movimientos'),
        apiFetch('/api/bodegas'),
        apiFetch('/api/productos'),
        apiFetch('/api/usuarios')
    ]).then(([movimientos, bodegas, productos, usuarios]) => {

        const opcionesBodegas = bodegas.map(b => `<option value="${b.id}">${b.nombre}</option>`).join('');
        const opcionesProductos = productos.map(p => `<option value="${p.id}">${p.nombre}</option>`).join('');
        const opcionesUsuarios = usuarios.map(u => `<option value="${u.id}">${u.nombre}</option>`).join('');

        const html = `
        <div class="form-card">
            <h3>Nuevo Movimiento</h3>
            <select id="m-tipo">
                <option value="ENTRADA">ENTRADA</option>
                <option value="SALIDA">SALIDA</option>
                <option value="TRANSFERENCIA">TRANSFERENCIA</option>
            </select>
            <select id="m-usuario">${opcionesUsuarios}</select>
            <select id="m-origen">
                <option value="">-- Sin bodega origen --</option>
                ${opcionesBodegas}
            </select>
            <select id="m-destino">
                <option value="">-- Sin bodega destino --</option>
                ${opcionesBodegas}
            </select>
            <select id="m-producto">${opcionesProductos}</select>
            <input id="m-cantidad" placeholder="Cantidad" type="number" />
            <button onclick="crearMovimiento()">Registrar Movimiento</button>
        </div>
        <div class="card-grid">
            ${movimientos.map(m => `
                <div class="card">
                    <h3>Movimiento #${m.id}</h3>
                    <span class="badge ${m.tipo === 'ENTRADA' ? 'badge-entrada' : m.tipo === 'SALIDA' ? 'badge-salida' : 'badge-transfer'}">
                        ${m.tipo}
                    </span>
                    <p>Fecha: ${new Date(m.fecha).toLocaleString()}</p>
                    <p>Usuario: ${m.usuario.nombre}</p>
                    <p>Origen: ${m.bodegaOrigen ? m.bodegaOrigen.nombre : 'N/A'}</p>
                    <p>Destino: ${m.bodegaDestino ? m.bodegaDestino.nombre : 'N/A'}</p>
                    <p>Productos: ${m.detalles.map(d => `${d.producto.nombre} (${d.cantidad})`).join(', ')}</p>
                </div>
            `).join('')}
        </div>`;
        document.getElementById('lista-movimientos').innerHTML = html;
    });
}

function crearMovimiento() {
    const origenVal = document.getElementById('m-origen').value;
    const destinoVal = document.getElementById('m-destino').value;

    const body = {
        tipo: document.getElementById('m-tipo').value,
        usuarioId: parseInt(document.getElementById('m-usuario').value),
        bodegaOrigenId: origenVal ? parseInt(origenVal) : null,
        bodegaDestinoId: destinoVal ? parseInt(destinoVal) : null,
        detalles: [{
            productoId: parseInt(document.getElementById('m-producto').value),
            cantidad: parseInt(document.getElementById('m-cantidad').value)
        }]
    };

    apiFetch('/api/movimientos', 'POST', body)
        .then(() => cargarMovimientos())
        .catch(err => alert('Error: ' + err.message));
}

// ==================== REPORTE ====================

function cargarReporte() {
    apiFetch('/api/reportes/resumen').then(data => {
        const stockHtml = `
            <h3>Stock por Bodega</h3>
            <div class="card-grid">
                ${data.stockPorBodega.map(b => `
                    <div class="card">
                        <h3>${b.nombreBodega}</h3>
                        <p>Ubicacion: ${b.ubicacion}</p>
                        <p>Productos: ${b.totalProductos}</p>
                        <p>Stock Total: ${b.totalStock}</p>
                    </div>
                `).join('')}
            </div>`;

        const movidosHtml = `
            <h3 style="margin-top:24px">Productos mas Movidos</h3>
            <div class="card-grid">
                ${data.productosMasMovidos.map((p, i) => `
                    <div class="card">
                        <h3>#${i + 1} ${p.nombreProducto}</h3>
                        <p>Categoria: ${p.categoria}</p>
                        <p>Total movido: ${p.totalMovido} unidades</p>
                    </div>
                `).join('')}
            </div>`;

        document.getElementById('reporte-stock').innerHTML = stockHtml;
        document.getElementById('reporte-movidos').innerHTML = movidosHtml;
    });
}

// ==================== AUDITORIAS ====================

function cargarAuditorias() {
    apiFetch('/api/auditorias').then(data => {

        function parsearValor(texto) {
            if (!texto) return '';

            const match = texto.match(/\((.+)\)/s);
            if (!match) return `<p>${texto}</p>`;

            const contenido = match[1];
            const campos = contenido.split(/,(?![^(]*\))/);

            return campos.map(campo => {
                const partes = campo.trim().split('=');
                if (partes.length >= 2) {
                    const clave = partes[0].trim();
                    const valor = partes.slice(1).join('=').trim();

                    if (clave === 'id') return '';
                    if (clave === 'password') return '';
                    if (valor === 'null') return '';

                    const objetoMatch = valor.match(/\w+\((.+)\)/s);
                    if (objetoMatch) {
                        const nombreMatch = objetoMatch[1].match(/nombre=([^,)]+)/);
                        const valorMostrar = nombreMatch ? nombreMatch[1] : valor;
                        return `<p><strong>${clave}:</strong> ${valorMostrar}</p>`;
                    }

                    if (clave === 'fecha') {
                        const fecha = new Date(valor);
                        return `<p><strong>fecha:</strong> ${fecha.toLocaleString()}</p>`;
                    }

                    return `<p><strong>${clave}:</strong> ${valor}</p>`;
                }
                return '';
            }).join('');
        }

        const html = `<div class="card-grid">
            ${data.map(a => `
                <div class="card">
                    <h3>${a.tipoOperacion} - ${a.entidadAfectada}</h3>
                    <p>Fecha: ${new Date(a.fecha).toLocaleString()}</p>
                    <p>Usuario: ${a.usuario}</p>
                    ${a.valorNuevo ? `
                    <hr style="margin:10px 0; border:none; border-top:1px solid #eee"/>
                    ${parsearValor(a.valorNuevo)}` : ''}
                </div>
            `).join('')}
        </div>`;
        document.getElementById('lista-auditorias').innerHTML = html;
    });
}