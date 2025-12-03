import { useState, useEffect } from 'react'
import './App.css'

function App() {
    const [libros, setLibros] = useState([])

    // Cargar libros
    useEffect(() => {
        fetch('http://localhost:8080/api/libros')
            .then(response => response.json())
            .then(data => setLibros(data))
            .catch(error => console.error("Error:", error))
    }, [])

    // Función simple para prestar
    const prestar = async (id) => {
        const usuarioId = prompt("ID del usuario:");
        if (!usuarioId) return;

        await fetch('http://localhost:8080/api/prestamos', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ libroId: id, usuarioId: parseInt(usuarioId) })
        });
        alert("Intento de préstamo enviado. Recarga para ver cambios.");
    }

    return (
        <div style={{ padding: '20px' }}>
            <h1>📚 Biblioteca Activa</h1>
            <ul>
                {libros.map((libro) => (
                    <li key={libro.id} style={{ marginBottom: '10px', padding: '10px', border: '1px solid #ccc' }}>
                        <strong>{libro.titulo}</strong> - {libro.disponible ? '✅ Disponible' : '❌ Prestado'}

                        {/* Solo mostramos el botón si está disponible */}
                        {libro.disponible && (
                            <button
                                onClick={() => prestar(libro.id)}
                                style={{ marginLeft: '10px', background: 'blue', color: 'white' }}
                            >
                                Prestar
                            </button>
                        )}
                    </li>
                ))}
            </ul>
        </div>
    )
}

export default App