import { useState, useEffect } from 'react'
import './App.css'

function App() {
    const [libros, setLibros] = useState([])
    const [usuarios, setUsuarios] = useState([]) // 1. Lista para guardar los usuarios
    const [libroSeleccionado, setLibroSeleccionado] = useState(null) // 2. Cuál libro estamos prestando
    const [usuarioElegido, setUsuarioElegido] = useState("") // 3. Qué usuario seleccionaste

    // Cargar datos al iniciar
    useEffect(() => {
        cargarLibros();
        cargarUsuarios(); // También cargamos usuarios
    }, [])

    const cargarLibros = () => {
        fetch('http://localhost:8080/api/libros')
            .then(res => res.json())
            .then(data => setLibros(data))
            .catch(err => console.error("Error libros:", err))
    }

    // --- NUEVA FUNCIÓN: TRAER USUARIOS DE JAVA ---
    const cargarUsuarios = () => {
        fetch('http://localhost:8080/api/usuarios')
            .then(res => res.json())
            .then(data => setUsuarios(data))
            .catch(err => console.error("Error usuarios:", err))
    }

    // A. Abrir la ventanita modal
    const iniciarPrestamo = (libro) => {
        setLibroSeleccionado(libro);
        setUsuarioElegido(""); // Resetear selección anterior
    }

    // B. Confirmar el préstamo (Botón verde de la modal)
    const confirmarPrestamo = async () => {
        if (!usuarioElegido) {
            alert("⚠️ Por favor selecciona un usuario de la lista.");
            return;
        }

        // Enviamos el préstamo al Backend
        const response = await fetch('http://localhost:8080/api/prestamos', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                libroId: libroSeleccionado.id,
                usuarioId: parseInt(usuarioElegido)
            })
        });

        if (response.ok) {
            alert("✅ Libro prestado exitosamente.");
            setLibroSeleccionado(null); // Cerrar modal
            cargarLibros(); // Actualizar lista
        } else {
            const errorMsg = await response.text();
            alert("Error: " + errorMsg);
        }
    }

    // --- FUNCIÓN DEVOLVER ---
    const devolver = async (id) => {
        if (!confirm("¿Confirmar devolución?")) return;
        const res = await fetch(`http://localhost:8080/api/prestamos/devolver/${id}`, { method: 'PUT' });
        if (res.ok) {
            alert("🔙 Libro devuelto.");
            cargarLibros();
        }
    }

    return (
        <div style={{ padding: '20px', fontFamily: 'sans-serif', minHeight: '100vh', paddingBottom: '50px' }}>
            <h1 style={{ textAlign:'center', marginBottom: '30px' }}>Gestión de Biblioteca</h1>

            {/* GRILLA DE LIBROS */}
            <div style={{ display:'grid', gap:'20px', gridTemplateColumns:'repeat(auto-fill, minmax(300px, 1fr))' }}>
                {libros.map((libro) => (
                    <div key={libro.id} style={{
                        padding: '20px', border: '1px solid #ddd', borderRadius:'12px',
                        color: '#333', backgroundColor: libro.disponible ? '#f0fff4' : '#fff5f5',
                        boxShadow: '0 4px 6px rgba(0,0,0,0.1)'
                    }}>
                        <h3 style={{ marginTop: 0, color: '#000' }}>{libro.titulo}</h3>
                        <p style={{ margin: '5px 0' }}><strong>Autor:</strong> {libro.autor}</p>

                        <div style={{ marginTop: '15px', marginBottom: '15px' }}>
                <span style={{ fontWeight: 'bold', color: libro.disponible ? 'green' : 'red' }}>
                    {libro.disponible ? 'Disponible' : 'Prestado'}
                </span>
                        </div>

                        {libro.disponible ? (
                            <button onClick={() => iniciarPrestamo(libro)}
                                    style={{ width:'100%', padding:'12px', background: '#28a745', color: 'white', border:'none', borderRadius:'6px', cursor:'pointer', fontWeight:'bold' }}>
                                Prestar
                            </button>
                        ) : (
                            <button onClick={() => devolver(libro.id)}
                                    style={{ width:'100%', padding:'12px', background: '#dc3545', color: 'white', border:'none', borderRadius:'6px', cursor:'pointer', fontWeight:'bold' }}>
                                Devolver
                            </button>
                        )}
                    </div>
                ))}
            </div>

            {/* --- AQUÍ ESTÁ LA VENTANA MODAL (EL TRUCO VISUAL) --- */}
            {libroSeleccionado && (
                <div style={{
                    position: 'fixed', top: 0, left: 0, width: '100%', height: '100%',
                    backgroundColor: 'rgba(0,0,0,0.6)', // Fondo oscuro transparente
                    display: 'flex', justifyContent: 'center', alignItems: 'center', zIndex: 1000
                }}>
                    <div style={{
                        backgroundColor: 'white', padding: '30px', borderRadius: '10px',
                        width: '400px', maxWidth: '90%', boxShadow: '0 5px 15px rgba(0,0,0,0.3)'
                    }}>
                        <h2 style={{color: 'black', marginTop: 0}}>Prestar Libro</h2>
                        <p style={{color: '#555', marginBottom: '20px'}}>
                            Vas a prestar: <strong>{libroSeleccionado.titulo}</strong>
                        </p>

                        <label style={{display: 'block', marginBottom: '10px', color: '#333', fontWeight: 'bold'}}>
                            Selecciona el Usuario:
                        </label>

                        {/* LISTA DESPLEGABLE CONECTADA A MYSQL */}
                        <select
                            style={{ width: '100%', padding: '12px', marginBottom: '25px', fontSize: '16px', borderRadius: '5px', border: '1px solid #ccc', color: '#333', backgroundColor: 'white' }}
                            value={usuarioElegido}
                            onChange={(e) => setUsuarioElegido(e.target.value)}
                        >
                            <option value="">-- Elige un usuario --</option>
                            {usuarios.map(u => (
                                <option key={u.id} value={u.id}>
                                    {u.nombre} (ID: {u.id})
                                </option>
                            ))}
                        </select>

                        <div style={{ display: 'flex', gap: '10px', justifyContent: 'flex-end' }}>
                            <button
                                onClick={() => setLibroSeleccionado(null)}
                                style={{ padding: '10px 20px', background: '#6c757d', border: 'none', borderRadius: '5px', cursor: 'pointer', color: 'white' }}
                            >
                                Cancelar
                            </button>
                            <button
                                onClick={confirmarPrestamo}
                                style={{ padding: '10px 20px', background: '#007bff', color: 'white', border: 'none', borderRadius: '5px', cursor: 'pointer', fontWeight: 'bold' }}
                            >
                                Confirmar Préstamo
                            </button>
                        </div>
                    </div>
                </div>
            )}

        </div>
    )
}

export default App