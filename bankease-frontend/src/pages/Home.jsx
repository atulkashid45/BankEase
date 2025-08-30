import React, { useEffect, useState } from 'react'
import { api } from '../api/client'
import { Link } from 'react-router-dom'

export default function Home() {
  const [accounts, setAccounts] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [amount, setAmount] = useState('')
  const [selectedAccountId, setSelectedAccountId] = useState('')

  async function load() {
    try {
      const res = await api.get('/api/accounts/me')
      setAccounts(res.data)
      if (res.data?.length) setSelectedAccountId(res.data[0].id)
    } catch (e) {
      setError('Failed to load accounts')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => { load() }, [])

  async function createAccount() {
    await api.post('/api/accounts')
    await load()
  }

  async function deposit() {
    if (!selectedAccountId || !amount) return
    await api.post('/api/accounts/deposit', { accountId: Number(selectedAccountId), amount: Number(amount), description: 'Deposit via UI' })
    setAmount('')
    await load()
  }

  async function withdraw() {
    if (!selectedAccountId || !amount) return
    await api.post('/api/accounts/withdraw', { accountId: Number(selectedAccountId), amount: Number(amount), description: 'Withdraw via UI' })
    setAmount('')
    await load()
  }

  if (loading) return <p>Loading...</p>
  if (error) return <div className="alert alert-danger">{error}</div>

  return (
    <div className="row">
      <div className="col-lg-8">
        <div className="card shadow-sm mb-3">
          <div className="card-body">
            <div className="d-flex justify-content-between align-items-center">
              <h4>Your Accounts</h4>
              <button className="btn btn-success" onClick={createAccount}>+ New Account</button>
            </div>
            <div className="list-group mt-3">
              {accounts.map(a => (
                <div key={a.id} className="list-group-item d-flex justify-content-between align-items-center">
                  <div>
                    <div className="fw-bold">{a.accountNumber}</div>
                    <div className="text-muted small">{a.type}</div>
                  </div>
                  <div className="text-end">
                    <div className="fs-5">₹{a.balance}</div>
                    <Link className="btn btn-link p-0 mt-1" to={`/transactions/${a.id}`}>View transactions</Link>
                  </div>
                </div>
              ))}
              {!accounts.length && <div className="text-muted">No accounts yet. Create one!</div>}
            </div>
          </div>
        </div>
      </div>
      <div className="col-lg-4">
        <div className="card shadow-sm">
          <div className="card-body">
            <h5>Quick Actions</h5>
            <div className="mb-2">
              <label className="form-label">Select Account</label>
              <select className="form-select" value={selectedAccountId} onChange={e => setSelectedAccountId(e.target.value)}>
                {accounts.map(a => <option key={a.id} value={a.id}>{a.accountNumber}</option>)}
              </select>
            </div>
            <div className="mb-2">
              <label className="form-label">Amount</label>
              <input className="form-control" value={amount} onChange={e => setAmount(e.target.value)} placeholder="Enter amount" />
            </div>
            <div className="d-flex gap-2">
              <button className="btn btn-outline-primary" onClick={deposit}>Deposit</button>
              <button className="btn btn-outline-warning" onClick={withdraw}>Withdraw</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}
