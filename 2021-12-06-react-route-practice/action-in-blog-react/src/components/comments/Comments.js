import { useCallback, useState } from 'react';
import { useParams } from 'react-router';
import { useEffect } from 'react/cjs/react.development';
import useHttp from '../../hooks/use-http';
import { getAllComments } from '../../lib/api';
import LoadingSpinner from '../UI/LoadingSpinner';

import classes from './Comments.module.css';
import NewCommentForm from './NewCommentForm';
import CommentsList from './CommentsList';

const Comments = () => {

  const { quoteId } = useParams();
  const { sendRequest, status, error, data: loadedComments } = useHttp(getAllComments);
  const [isAddingComment, setIsAddingComment] = useState(false);

  useEffect(() => {
    sendRequest(quoteId);
  }, [sendRequest, quoteId]);

  const startAddCommentHandler = () => {
    setIsAddingComment(true);
  };

  const addedCommentHandler = useCallback(() => {
    sendRequest(quoteId);
  }, [sendRequest, quoteId]);

  let comment;
  if (status === 'pending') {
    comment = (
      <div className="centered">
        <LoadingSpinner />
      </div>
    );
  }

  if (status === 'completed' && (loadedComments && loadedComments.length > 0)) {
    comment = <CommentsList comments={loadedComments} />
  }

  if (status === 'completed' && (!loadedComments || loadedComments.length === 0)) {
    comment = <p className="centered">No comments were added yet.</p>
  }

  if (error) {
    return (
      <p className='centered focused'>
        {error}
      </p>
    );
  }

  return (
    <section className={classes.comments}>
      <h2>User Comments</h2>
      {!isAddingComment && (
        <button className='btn' onClick={startAddCommentHandler}>
          Add a Comment
        </button>
      )}
      {isAddingComment && <NewCommentForm onAddedComment={addedCommentHandler} />}
      {comment}
    </section>
  );
};

export default Comments;
