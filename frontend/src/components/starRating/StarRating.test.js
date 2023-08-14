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
