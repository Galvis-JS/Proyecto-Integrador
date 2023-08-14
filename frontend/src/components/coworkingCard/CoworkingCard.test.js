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
