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
