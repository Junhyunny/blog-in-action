import { useEffect } from "react";
import { Fragment } from "react";
import { useHistory } from "react-router";
import QuoteForm from '../components/quotes/QuoteForm';
import useHttp from "../hooks/use-http";
import { addQuote } from '../lib/api';

const NewQuote = () => {

    const { sendRequest, status } = useHttp(addQuote);

    const history = useHistory();

    useEffect(() => {
        if (status === 'completed') {
            history.push({
                pathname: '/quotes'
            });
        }
    }, [status, history]);

    const addQuoteHandler = (data) => {
        sendRequest(data);
    };

    return (
        <Fragment>
            <QuoteForm isLoading={status === 'pending'} onAddQuote={addQuoteHandler} />
        </Fragment>
    );
};

export default NewQuote;