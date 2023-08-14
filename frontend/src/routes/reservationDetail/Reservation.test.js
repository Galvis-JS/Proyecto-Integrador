import { MemoryRouter, Route, Routes } from 'react-router-dom';
import { act, render, waitFor } from "@testing-library/react";

import AppContext from '../../context/AppContext';
import { ReservationDetail } from "./ReservationDetail";

const coworkingResponse = {
    "idCoworking": 1,
    "name": "Gamer",
    "address": "Av. Pres. Figueroa Alcorta 7597, C1428 CABA",
    "description": "Nuestra oficina 'Gamer' es un espacio único y emocionante diseñado específicamente para el sector de los videojuegos.",
    "image": "https://comision3-equipo3.s3.amazonaws.com/img/N9KrZsyQCE.jpg;https://comision3-equipo3.s3.amazonaws.com/img/OYpt1w4vka.jpg;https://comision3-equipo3.s3.amazonaws.com/img/U3cpHIw1gT.jpg;https://comision3-equipo3.s3.amazonaws.com/img/DdZNtBEbnc.jpg;https://comision3-equipo3.s3.amazonaws.com/img/N8pOp64Nuh.jpg",
    "rating": {
        "rating": 4.0,
        "valoraciones": 2
    },
    "coworkingRulesPolicy": "No se permiten comportamientos disruptivos. Las áreas comunes deben ser dejadas tal como se encontraron",
    "healthSafetyPolicy": "Todos los miembros deben familiarizarse con las salidas de emergencia y los procedimientos de evacuación",
    "cancellationPolicy": "Generalmente, las cuotas de membresía no son reembolsables una vez que han sido pagadas.",
    "city": {
        "idCity": 1,
        "name": "Buenos Aires",
        "country": "Argentina"
    },
    "category": {
        "idCategory": 1,
        "name": "Coworking Sectorial",
        "description": "",
        "image": "https://comision3-equipo3.s3.amazonaws.com/img/ZhravBtBiM.jpg",
        "results": 2
    },
    "latitude": -34.546104047603706,
    "longitude": -58.451264691542804,
    "coworkingFacilities": [
        {
            "id": 3,
            "facility": {
                "id": 1,
                "name": "Aire acondicionado",
                "status": true
            }
        }

    ]
};

const reservationResponse = [
    {
        "idReserve": 3,
        "start_date": "2023-06-21T07:30:00",
        "end_date": "2023-06-22T07:30:00",
    }
];

const contextValue = {
    checkIn: '21/06/2023',
    checkOut: '25/06/2023',
    setCheckOut: jest.fn(),
    setCheckIn: jest.fn(),
    globalState: {
        nombre: 'Juan',
        apellido: 'Perez',
        email: 'juan.perez@gmail.com'
    }
};

global.fetch = jest.fn((url) => {
    const parsedUrl = new URL(url);
    if (parsedUrl.pathname === '/api/Products/1') {
        return Promise.resolve({
            json: () => Promise.resolve(coworkingResponse),
        })
    }
    if (parsedUrl.pathname === '/api/Reserves/coworking') {
        return Promise.resolve({
            json: () => Promise.resolve(reservationResponse),
        })
    }

    return Promise.resolve({
        json: () => Promise.reject(new Error(`Bad fetch for ${url}`)),
    });
});

const renderReservationDetail = (initialPath = '/reservation/1') => {
    return render(
        <MemoryRouter initialEntries={[initialPath]}>
            <AppContext.Provider value={contextValue}>
                <Routes>
                    <Route path="/reservation/:id" element={<ReservationDetail />} />
                </Routes>
            </AppContext.Provider>
        </MemoryRouter>
    );
};

describe("ReservationDetail component", () => {

    beforeEach(() => {
        fetch.mockClear();
    });

    it("renders without crashing", async () => {
        await act(async () => {
            renderReservationDetail();
        });
    });

    // Añadir más pruebas para verificar la funcionalidad del componente

    it("calls fetch when the component is rendered", async () => {
        await act(async () => {
            renderReservationDetail();
        });
    
        await waitFor(() => {
            expect(fetch).toHaveBeenCalledTimes(2);
        });
    });

});

