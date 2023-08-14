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
