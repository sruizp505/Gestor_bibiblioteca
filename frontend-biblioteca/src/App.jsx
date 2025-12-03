import { useState, useEffect } from 'react'
import './App.css'

function App() {
    // --- CONFIGURACIÓN "NUBE VS LOCAL" ---
    // Si existe una variable de entorno (Nube), úsala. Si no, usa localhost.
    const API_BASE_URL = import.meta.env.VITE_BACKEND_URL || "https://api-biblioteca-4r8h.onrender.com";

    const [libros, setLibros] = useState([])
    const [usuarios, setUsuarios] = useState([])
    const [libroSeleccionado, setLibroSeleccionado] = useState(null)
    const [usuarioElegido, setUsuarioElegido] = useState("")

    useEffect(() => {
        cargarLibros();
        cargarUsuarios();
    }, [])

    const cargarLibros = () => {
        fetch(`${API_BASE_URL}/api/libros`)
            .then(res => res.json())
            .then(data => setLibros(data))
            .catch(err => console.error("Error libros:", err))
    }

    const cargarUsuarios = () => {
        fetch(`${API_BASE_URL}/api/usuarios`)
            .then(res => res.json())
            .then(data => setUsuarios(data))
            .catch(err => console.error("Error usuarios:", err))
    }

    const iniciarPrestamo = (libro) => {
        setLibroSeleccionado(libro);
        setUsuarioElegido("");
    }

    const confirmarPrestamo = async () => {
        if (!usuarioElegido) {
            alert("⚠️ Por favor selecciona un usuario.");
            return;
        }

        const response = await fetch(`${API_BASE_URL}/api/prestamos`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                libroId: libroSeleccionado.id,
                usuarioId: parseInt(usuarioElegido)
            })
        });

        if (response.ok) {
            alert("✅ Libro prestado exitosamente.");
            setLibroSeleccionado(null);
            cargarLibros();
        } else {
            const errorMsg = await response.text();
            alert("Error: " + errorMsg);
        }
    }

    const devolver = async (id) => {
        if (!confirm("¿Confirmar devolución?")) return;
        const res = await fetch(`${API_BASE_URL}/api/prestamos/devolver/${id}`, { method: 'PUT' });
        if (res.ok) {
            alert("🔙 Libro devuelto.");
            cargarLibros();
        }
    }

    return (
        <div style={{ padding: '20px', fontFamily: 'sans-serif', minHeight: '100vh', paddingBottom: '50px' }}>
            <h1 style={{ textAlign:'center', marginBottom: '30px' }}>Gestión de Biblioteca</h1>

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

            {libroSeleccionado && (
                <div style={{
                    position: 'fixed', top: 0, left: 0, width: '100%', height: '100%',
                    backgroundColor: 'rgba(0,0,0,0.6)',
                    display: 'flex', justifyContent: 'center', alignItems: 'center', zIndex: 1000
                }}>
                    <div style={{ backgroundColor: 'white', padding: '30px', borderRadius: '10px', width: '400px', maxWidth: '90%' }}>
                        <h2 style={{color: 'black', marginTop: 0}}>Prestar Libro</h2>
                        <select
                            style={{ width: '100%', padding: '12px', marginBottom: '25px', fontSize: '16px', borderRadius: '5px', border: '1px solid #ccc', color: '#333', backgroundColor: 'white' }}
                            value={usuarioElegido} onChange={(e) => setUsuarioElegido(e.target.value)}
                        >
                            <option value="">-- Elige un usuario --</option>
                            {usuarios.map(u => (<option key={u.id} value={u.id}>{u.nombre}</option>))}
                        </select>
                        <div style={{ display: 'flex', gap: '10px', justifyContent: 'flex-end' }}>
                            <button onClick={() => setLibroSeleccionado(null)} style={{ padding: '10px 20px', background: '#6c757d', border: 'none', borderRadius: '5px', cursor: 'pointer', color: 'white' }}>Cancelar</button>
                            <button onClick={confirmarPrestamo} style={{ padding: '10px 20px', background: '#007bff', color: 'white', border: 'none', borderRadius: '5px', cursor: 'pointer', fontWeight: 'bold' }}>Confirmar</button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    )
}
export default App