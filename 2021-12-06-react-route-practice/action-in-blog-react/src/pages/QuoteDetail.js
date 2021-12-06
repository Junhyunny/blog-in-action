import { Fragment } from "react";
import { Route, useParams } from "react-router";
import { Link, useRouteMatch } from "react-router-dom";
import { useEffect } from "react/cjs/react.development";

import Comments from '../components/comments/Comments';
import HighlightedQuote from "../components/quotes/HighlightedQuote";
import LoadingSpinner from "../components/UI/LoadingSpinner";
import useHttp from "../hooks/use-http";
import { getSingleQuote } from "../lib/api";

const QuoteDetail = () => {
    const match = useRouteMatch();
    const params = useParams();
    const { quoteId } = params;
    const { sendRequest, status, error, data: loadedQuote } = useHttp(getSingleQuote, true);
    useEffect(() => {
        sendRequest(quoteId);
    }, [sendRequest, quoteId]);
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
    if(!loadedQuote) {
        return <p className='centered'>No Quote found.</p>
    }
    return (
        <Fragment>
            <HighlightedQuote text={loadedQuote.text} author={loadedQuote.author} />
            <Route path={`${match.path}`} exact>
                <div className='centered'>
                    <Link className='btn--flat' to={`${match.url}/comments`}>Load Comments</Link>
                </div>
            </Route>
            <Route path={`${match.path}/comments`}>
                <Comments />
            </Route>
        </Fragment>
    );
};

export default QuoteDetail;