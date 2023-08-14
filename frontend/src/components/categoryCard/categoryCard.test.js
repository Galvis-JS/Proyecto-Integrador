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
