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
        getRolesGlobalState: jest.fn(),       
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
        mockContext.getRolesGlobalState.mockReturnValue([]);
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
