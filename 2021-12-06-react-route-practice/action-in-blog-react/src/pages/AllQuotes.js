import { Fragment } from 'react';
import { useEffect } from 'react/cjs/react.development';
import QuoteList from '../components/quotes/QuoteList';
import LoadingSpinner from '../components/UI/LoadingSpinner';
import useHttp from '../hooks/use-http';
import { getAllQuotes } from '../lib/api';
import NoQuotesFound from '../components/quotes/NoQuotesFound';

const DUMMY_QUOTES = [
    { id: 'q1', author: 'MAX', text: 'Learning React is fun.' },
    { id: 'q2', author: 'MAX Hello', text: 'Learning React is great.' },
];

const AllQuotes = () => {

    const { sendRequest, status, data: loadedQuotes, error } = useHttp(getAllQuotes, true);

    useEffect(() => {
        sendRequest();
    }, [sendRequest]);

    if(status === 'pending') {
        return (
            <div className="centered">
                <LoadingSpinner />
            </div>
        );
    }

    if(error) {
        return (
            <p className='centered focused'>
                {error}
            </p>
        );
    }

    if(status === 'completed' && (!loadedQuotes || loadedQuotes.length === 0)) {
        return <NoQuotesFound />
    }

    return (
        <Fragment>
            <QuoteList quotes={loadedQuotes} />
        </Fragment>
    );
};

export default AllQuotes;