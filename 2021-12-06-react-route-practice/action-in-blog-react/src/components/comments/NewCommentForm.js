import { useRef } from 'react';
import { createPortal } from 'react-dom';
import { useParams } from 'react-router';
import { useEffect } from 'react/cjs/react.development';
import useHttp from '../../hooks/use-http';
import { addComment } from '../../lib/api';
import LoadingSpinner from '../UI/LoadingSpinner';

import classes from './NewCommentForm.module.css';

const NewCommentForm = (props) => {

  const commentTextRef = useRef();
  const { quoteId } = useParams();
  const { sendRequest, status, error } = useHttp(addComment);
  const { onAddedComment } = props;

  useEffect(() => {
    if (status === 'completed' && !error) {
      onAddedComment();
    }
  }, [status, error]);

  const submitFormHandler = (event) => {
    event.preventDefault();
    sendRequest({
      commentData: commentTextRef.current.value,
      quoteId,
    });
  };

  return (
    <form className={classes.form} onSubmit={submitFormHandler}>
      {status === 'pending' && <div className="cented"><LoadingSpinner /></div>}
      <div className={classes.control} onSubmit={submitFormHandler}>
        <label htmlFor='comment'>Your Comment</label>
        <textarea id='comment' rows='5' ref={commentTextRef}></textarea>
      </div>
      <div className={classes.actions}>
        <button className='btn'>Add Comment</button>
      </div>
    </form>
  );
};

export default NewCommentForm;
