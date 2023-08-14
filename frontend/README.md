# Digital Booking
## Frontend
## Unit Tests

Los tests unitarios en el area de Frontend se realizaron utilizando el framework Jest.Los tests se realizaron en los siguientes componentes: Footer, Header, CategoryCard, CoworkingCard, CoworkingRecomendation, ReservationCalendar, StarRating.

### Tests unitarios: Footer

```
import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import { Footer } from './Footer';

describe('Footer Component', () => {
  it('renders correctly', () => {
    render(<Footer />);
    
    // Verifica que el texto de copyright esté presente.
    const copyrightText = screen.getByText('@2023 Digital Booking');
    expect(copyrightText).toBeInTheDocument();
    
    // Verifica que los íconos estén presentes.
    const facebookIcon = document.querySelector('#icono-fb');
    const linkedinIcon = document.querySelector('#icono-in');
    const twitterIcon = document.querySelector('#icono-tw');
    const instagramIcon = document.querySelector('#icono-inst');
    
    expect(facebookIcon).toBeInTheDocument();
    expect(linkedinIcon).toBeInTheDocument();
    expect(twitterIcon).toBeInTheDocument();
    expect(instagramIcon).toBeInTheDocument();
  });
});

```

### Tests Unitarios: Header

```
import React from 'react';
import { render, fireEvent, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom';
import { BrowserRouter as Router } from 'react-router-dom';
import { Header } from './Header';
import AppContext from '../../context/AppContext';

// Mock para SweetAlert2
jest.mock('sweetalert2', () => ({
    fire: jest.fn(),
}));

// Mock para useNavigate
jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'), 
    useNavigate: () => jest.fn(), 
}));

describe('Header component', () => {
    const mockContext = {
        isAuthGlobalState: jest.fn(),
        signOf: jest.fn(),
        getNameGlobalState: jest.fn(),
        getSurnameGlobalState: jest.fn(),
        setShowResults: jest.fn(),
    };

    beforeEach(() => {
        jest.clearAllMocks();
    });

    it('renders the Header component', () => {
        const { container } = render(
            <AppContext.Provider value={mockContext}>
                <Router>
                    <Header />
                </Router>
            </AppContext.Provider>
        );
        const logo = container.querySelector('.logo span');
        expect(logo.textContent).toBe('Encuentra tu espacio de trabajo ideal');
    });

    it('shows correct buttons when user is not authenticated', () => {
        mockContext.isAuthGlobalState.mockReturnValue(false);
        const { container } = render(
            <AppContext.Provider value={mockContext}>
                <Router>
                    <Header />
                </Router>
            </AppContext.Provider>
        );
        const createAccountLink = container.querySelector('a[href="/signup"]');
        const loginLink = container.querySelector('a[href="/login"]');
        expect(createAccountLink).toBeInTheDocument();
        expect(loginLink).toBeInTheDocument();
    });

    it('shows correct user info when user is authenticated', () => {
        mockContext.isAuthGlobalState.mockReturnValue(true);
        mockContext.getNameGlobalState.mockReturnValue('John');
        mockContext.getSurnameGlobalState.mockReturnValue('Doe');
        const { container } = render(
            <AppContext.Provider value={mockContext}>
                <Router>
                    <Header />
                </Router>
            </AppContext.Provider>
        );
        const avatarContainer = container.querySelector('.avatar-container .avatar');
        const name = container.querySelector('.avatar-container .nombre');
        expect(avatarContainer.textContent).toBe('JD');
        expect(name.textContent).toBe('John Doe');
    });

    // ... other tests ...
});

```
### Tests Unitarios: CategoryCard

```
import React from 'react';
import { render } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import { BrowserRouter as Router } from 'react-router-dom';
import { CategoryCard } from './categoryCard';

describe('CategoryCard component', () => {
    const props = {
        idCategory: '1',
        image: 'test.jpg',
        name: 'Test category',
        results: 10,
    };

    it('should render correctly', () => {
        const { container } = render(
            <Router>
                <CategoryCard {...props} />
            </Router>
        );

        expect(container.querySelector(".card-category h4")).toHaveTextContent('Test category');
        expect(container.querySelector(".card-category p")).toHaveTextContent('10 oficinas en esta categoria');
        expect(container.querySelector(".card-category-link")).toHaveAttribute('href', '/category/1');
        expect(container.querySelector(".card-category-img-top")).toHaveAttribute('src', 'test.jpg');
    });
});

```
### Tests Unitarios: CoworkingCard


```
import { render, screen } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import { CoworkingCard } from './CoworkingCard';

describe('CoworkingCard', () => {
  const product = {
    image: 'image1;image2',
    name: 'Co-working Space Name',
    category: {
      name: 'Co-working'
    },
    rating: {
      rating: 4.5,
      valoraciones: 100,
    },
    idCoworking: 1,
    address: '123 Coworking St',
    city: {
      country: 'Country'
    },
    description: 'A great place to work with amazing facilities and community.'
  };

  beforeEach(() => {
    render(
      <Router>
        <CoworkingCard product={product} />
      </Router>
    );
  });

  it('renders the co-working card', () => {
    const card = document.querySelector('.coworking-card');
    expect(card).toBeInTheDocument();
  });

  it('renders the correct co-working space name', () => {
    const name = document.querySelector('.coworking-card-top h3');
    expect(name.textContent).toBe('Co-working Space Name');
  });

  it('renders the correct category', () => {
    const category = document.querySelector('.coworking-card-top h4');
    expect(category.textContent).toBe('Co-working');
  });

  it('renders the correct address', () => {
    const address = document.querySelector('.coworking-card-body p');
    expect(address.textContent).toContain('123 Coworking St');
  });

  it('renders the correct country', () => {
    const country = document.querySelector('.coworking-card-body p');
    expect(country.textContent).toContain('Country');
  });

  it('renders the correct description', () => {
    const description = document.querySelector('.coworking-card-footer p');
    expect(description.textContent).toContain('A great place to work with amazing facilities and community.');
  });

  it('renders the "Ver Mas" button with the correct link', () => {
    const link = document.querySelector('.btn');
    expect(link.textContent).toBe('Ver Mas');
    expect(link.getAttribute('href')).toBe('/detail/1');
  });
});

```
### Tests Unitarios: CoworkingRecomendation

```
import { render, screen, waitFor } from '@testing-library/react';
import { act } from 'react-dom/test-utils';
import fetchMock from 'fetch-mock-jest';
import { CoworkingRecommendation } from './CoworkingRecommendation';
import { constants } from '../../services/constants';
import { MemoryRouter } from 'react-router-dom';

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

```
### Tests Unitarios: ReservationCalendar

```
import React from 'react';
import { render, screen } from '@testing-library/react';
import { within } from '@testing-library/dom';
import userEvent from '@testing-library/user-event';
import { DateRange } from 'react-date-range';
import ReservationCalendar from './ReservationCalendar';

jest.mock('react-date-range', () => ({
  DateRange: jest.fn((props) => {
    const { onChange, ranges } = props;
    return (
      <div role="daterange">
        <button
          onClick={() =>
            onChange({
              selection: {
                startDate: new Date(),
                endDate: new Date(),
                key: 'selection',
              },
            })
          }
        >
          Change Date Range
        </button>
        <div>{JSON.stringify(ranges)}</div>
      </div>
    );
  }),
}));

describe('ReservationCalendar', () => {
  it('renders without crashing', () => {
    render(<ReservationCalendar />);
    const dateRange = screen.getByRole('daterange');
    expect(dateRange).toBeInTheDocument();
  });

  it('calls onChange when the button is clicked', () => {
    render(<ReservationCalendar />);
    const button = screen.getByText(/Change Date Range/i);
    userEvent.click(button);
    const dateRange = screen.getByRole('daterange');
    const { getByText } = within(dateRange);
    const rangeString = getByText(/{"startDate":.*,"endDate":.*,"key":"selection"}/);
    expect(rangeString).toBeInTheDocument();
  });
});

```
### Tests Unitarios: StarRating

```
import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import fetchMock from 'fetch-mock-jest';
import StarRating from './StarRating';
import { constants } from '../../services/constants';

describe('StarRating', () => {
    const idCoworking = 1;

    beforeEach(() => {
        localStorage.clear();
        fetchMock.reset();
    });

    it('renders the initial rating and votes correctly', () => {
        render(<StarRating value={3.5} cantidadVotos={10} idCoworking={idCoworking} />);

        const rating = screen.getByText('3.5 (10)');
        expect(rating).toBeInTheDocument();
    });

    it('updates the rating and votes when a star is clicked', async () => {
        fetchMock.post(constants.RATING_ENDPOINT, { status: 200, body: {} });

        render(<StarRating value={3.5} cantidadVotos={10} idCoworking={idCoworking} />);

        const stars = document.querySelectorAll('.star');
        fireEvent.click(stars[4]); // clicking the 5th star

        expect(fetchMock.called(constants.RATING_ENDPOINT)).toBe(true);


        const loading = screen.getByText('Votando...');
        expect(loading).toBeInTheDocument();

        // Wait for loading to disappear
        await waitFor(() => {
            expect(screen.queryByText('Votando...')).not.toBeInTheDocument();
        });

        // Check localStorage after loading has disappeared
        expect(localStorage.getItem(`hasVoted-${idCoworking}`)).toBe('true');

    });

    it('shows a warning if the user has already voted', () => {
        localStorage.setItem(`hasVoted-${idCoworking}`, 'true');

        render(<StarRating value={3.5} cantidadVotos={10} idCoworking={idCoworking} />);

        const stars = document.querySelectorAll('.star');
        fireEvent.click(stars[4]); // clicking the 5th star

        expect(fetchMock.called(constants.RATING_ENDPOINT)).toBe(false); // no request should have been made

        // Add an assertion here to check if the warning message is shown
        // This might require mocking Swal.fire or checking if Swal.fire has been called with the correct arguments
    });
});

```





