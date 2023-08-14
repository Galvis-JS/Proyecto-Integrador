import { render, screen, waitFor } from '@testing-library/react';

import { CoworkingRecommendation } from './CoworkingRecommendation';
import { MemoryRouter } from 'react-router-dom';
import { act } from 'react-dom/test-utils';
import { constants } from '../../services/constants';
import fetchMock from 'fetch-mock-jest';

describe('CoworkingRecommendation', () => {
    beforeEach(() => {
        fetchMock.reset();
    });

    it('displays loading text while fetching data', async () => {
        fetchMock.get(constants.PRODUCTS_ENDPOINT, {}); // respond with an empty object to delay resolving the Promise

        await act(async () => {
            render(<CoworkingRecommendation />);
        })

        const loading = screen.getByText('Cargando...');
        expect(loading).toBeInTheDocument();
    });

    it('renders coworking recommendations after fetching data', async () => {
        const fakeData =[
            {
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
                    },
                    {
                        "id": 4,
                        "facility": {
                            "id": 2,
                            "name": "Wifi",
                            "status": true
                        }
                    },
                    {
                        "id": 5,
                        "facility": {
                            "id": 3,
                            "name": "Smart TV",
                            "status": true
                        }
                    },
                    {
                        "id": 6,
                        "facility": {
                            "id": 4,
                            "name": "Estacionamiento",
                            "status": true
                        }
                    },
                    {
                        "id": 7,
                        "facility": {
                            "id": 5,
                            "name": "Cafe",
                            "status": true
                        }
                    },
                    {
                        "id": 8,
                        "facility": {
                            "id": 6,
                            "name": "Impresora",
                            "status": true
                        }
                    }
                ]
            },
            {
                "idCoworking": 2,
                "name": "Moda",
                "address": "Av. Pellegrini 1500, S2000 Rosario",
                "description": "Nuestra oficina 'Moda' en Rosario se centra en el sector de la moda, proporcionando un espacio de coworking sectorial que satisface las necesidades de los diseñadores.",
                "image": "https://comision3-equipo3.s3.amazonaws.com/img/DUAehTcPDA.jpg;https://comision3-equipo3.s3.amazonaws.com/img/6KC4LVFq1K.jpg;https://comision3-equipo3.s3.amazonaws.com/img/woRd6dixRl.jpg;https://comision3-equipo3.s3.amazonaws.com/img/gXC7Gjhh4x.jpg;https://comision3-equipo3.s3.amazonaws.com/img/4pvhXDbojt.jpg",
                "rating": null,
                "coworkingRulesPolicy": "Cada miembro es responsable de la seguridad de sus propios bienes",
                "healthSafetyPolicy": "Los miembros pueden recibir visitantes, pero deben ser responsables de su comportamiento y asegurarse de que no interrumpan a los demás miembros",
                "cancellationPolicy": "Los miembros deben proporcionar un aviso de cancelación ",
                "city": {
                    "idCity": 2,
                    "name": "Rosario",
                    "country": "Argentina"
                },
                "category": {
                    "idCategory": 1,
                    "name": "Coworking Sectorial",
                    "description": "",
                    "image": "https://comision3-equipo3.s3.amazonaws.com/img/ZhravBtBiM.jpg",
                    "results": 2
                },
                "latitude": -32.955934558789075,
                "longitude": -60.64713387556239,
                "coworkingFacilities": [
                    {
                        "id": 9,
                        "facility": {
                            "id": 1,
                            "name": "Aire acondicionado",
                            "status": true
                        }
                    },
                    {
                        "id": 10,
                        "facility": {
                            "id": 2,
                            "name": "Wifi",
                            "status": true
                        }
                    },
                    {
                        "id": 11,
                        "facility": {
                            "id": 3,
                            "name": "Smart TV",
                            "status": true
                        }
                    },
                    {
                        "id": 12,
                        "facility": {
                            "id": 4,
                            "name": "Estacionamiento",
                            "status": true
                        }
                    }
                ]
            },
        ];

        fetchMock.get(constants.PRODUCTS_ENDPOINT, fakeData);

        await act(async () => {
            render(<MemoryRouter><CoworkingRecommendation /></MemoryRouter>);
        })

        await waitFor(() => {
            expect(screen.queryByText('Cargando...')).not.toBeInTheDocument();

            // You can add more specific assertions here depending on what's displayed in the CoworkingCard
            // Here's an example:
            fakeData.forEach((data) => {
                const card = screen.getByText(data.name);
                expect(card).toBeInTheDocument();
            });
        });
    });

});
